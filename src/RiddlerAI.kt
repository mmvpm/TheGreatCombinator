import kotlin.math.abs
import kotlin.random.Random

class RiddlerAI(override val length: Int = 4,
                override val maxDigit: Int = 6) : Riddler {

    private lateinit var answer: String

    private fun randomDigit(): Int = abs(Random.nextInt()) % maxDigit + 1

    override fun chooseNumber() {
        val result = StringBuilder(length)

        for (i in 0 until length) {
            result.append(randomDigit())
        }

        answer = result.toString()
    }

    override fun check(attempt: String): Pair<Int, Int> {
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

    override fun getCorrectAnswer(): String {
        return answer
    }

}