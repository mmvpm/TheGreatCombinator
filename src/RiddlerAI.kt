import kotlin.math.abs
import kotlin.random.Random

class RiddlerAI(override val length: Int = 4,
                override val maxDigit: Int = 6) : Riddler {

    private lateinit var answer: String


    override fun chooseNumber() {
        val result = StringBuilder(length)

        for (i in 0 until length) {
            result.append(utility.randomDigit(maxDigit))
        }

        answer = result.toString()
    }

    override fun check(attempt: String): Pair<Int, Int> {
        return utility.check(attempt, answer, length)
    }

    override fun getCorrectAnswer(): String {
        return answer
    }

}