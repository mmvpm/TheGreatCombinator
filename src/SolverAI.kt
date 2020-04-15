import java.util.*
import kotlin.math.abs
import kotlin.random.Random

class SolverAI(override val length: Int = 4,
               override val maxDigit: Int = 6) : Solver {

    private lateinit var lastAttempt: String
    private lateinit var lastResponse: Pair<Int, Int>

    private var possibleAnswers: Set<String> = setOf()


    private fun randomDigit(): Int = abs(Random.nextInt()) % maxDigit + 1

    private fun makeFirstAttempt(): String {
        val left: Int = length / 4
        val middle: Int = length / 2
        val right: Int = length - (left + middle)

        val attempt = StringBuilder(length)

        var digit: Int = randomDigit()
        for (bound in listOf(left, middle, right)) {
            for (i in 0 until bound) {
                attempt.append(digit)
            }

            var nextDigit: Int = digit
            while (nextDigit == digit) {
                nextDigit = randomDigit()
            }
            digit = nextDigit
        }

        return attempt.toString()
    }

    fun check(attempt: String, answer: String): Pair<Int, Int> {
        var aCount = 0
        var bCount = 0

        for (i in 0 until length) {
            if (attempt[i] == answer[i]) {
                aCount += 1
            }
            else if (attempt[i] in answer) {
                bCount += 1
            }
        }

        return Pair(aCount, bCount)
    }

    private fun countDifference(first: String, second: String): Int {
        return check(first, second).toList().sum()
    }

    override fun makeAttempt(): String {
        if (possibleAnswers.isEmpty()) {
            lastAttempt = makeFirstAttempt()
            return lastAttempt
        }

        var attempt: String = possibleAnswers.first()
        for (answer in possibleAnswers) {
            if (countDifference(attempt, lastAttempt) < countDifference(answer, lastAttempt)) {
                attempt = answer
            }
        }

        lastAttempt = attempt
        return lastAttempt
    }

    private fun generatePossible(prefix: String = "") {
        if (prefix.length == length) {
            if (check(lastAttempt, prefix) == lastResponse) {
                possibleAnswers = possibleAnswers.plus(prefix)
            }
            return
        }

        for (i in 1..maxDigit) {
            generatePossible(prefix + i.toString())
        }
    }

    override fun parseResponse(response: Pair<Int, Int>) {
        lastResponse = response

        if (possibleAnswers.isEmpty()) {
            generatePossible()
            return
        }

        var toRemove: List<String> = mutableListOf()
        for (answer in possibleAnswers) {
            if (check(lastAttempt, answer) != lastResponse) {
                toRemove = toRemove.plus(answer)
            }
        }
        for (answer in toRemove) {
            possibleAnswers = possibleAnswers.minus(answer)
        }
    }

}