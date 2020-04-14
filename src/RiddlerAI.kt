import kotlin.math.abs
import kotlin.random.Random

class RiddlerAI(override val length: Int = 4,
                override val maxDigit: Int = 6) : Riddler {

    lateinit var answer: String

    override fun chooseNumber() {
        val result = Array(length, {'0'})

        for (i in 0 until length) {
            result[i] = '0' + abs(Random.nextInt()) % maxDigit + 1
        }

        answer = result.joinToString(separator = "")
    }

    override fun check(attempt: String): Pair<Int, Int> {
        var aCount: Int = 0
        var bCount: Int = 0

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