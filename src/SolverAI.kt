import kotlin.math.abs
import kotlin.random.Random

class SolverAI(override val length: Int = 4,
               override val maxDigit: Int = 6) : Solver {

    private lateinit var lastAttempt: String
    private lateinit var lastResponse: Pair<Int, Int>

    private var possibleAnswers: Set<String> = setOf()


    private fun makeFirstAttempt(): String {
        val attempt = StringBuilder(length)

        val left: Int = length / 4
        val middle: Int = length / 2
        val right: Int = length - (left + middle)
        var digit: Int = utility.randomDigit(maxDigit)

        for (bound in listOf(left, middle, right)) {
            for (i in 0 until bound) {
                attempt.append(digit)
            }

            var nextDigit: Int = digit
            while (nextDigit == digit) {
                nextDigit = utility.randomDigit(maxDigit)
            }
            digit = nextDigit
        }

        return attempt.toString()
    }


    private fun countDifference(first: String, second: String): Int {
        return utility.check(first, second, length).toList().sum()
    }

    override fun makeAttempt(): String {
        if (possibleAnswers.isEmpty()) {
            lastAttempt = makeFirstAttempt()
            return lastAttempt
        }

        var best: String = possibleAnswers.first()
        for (answer in possibleAnswers) {
            if (countDifference(best, lastAttempt) < countDifference(answer, lastAttempt)) {
                best = answer
            }
        }

        lastAttempt = best
        return lastAttempt
    }


    private fun generatePossible(prefix: String = "") {
        if (prefix.length == length) {
            if (utility.check(lastAttempt, prefix, length) == lastResponse) {
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
            if (utility.check(lastAttempt, answer, length) != lastResponse) {
                toRemove = toRemove.plus(answer)
            }
        }
        for (answer in toRemove) {
            possibleAnswers = possibleAnswers.minus(answer)
        }
    }

}