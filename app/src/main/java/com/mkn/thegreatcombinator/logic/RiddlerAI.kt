import android.util.Log

class RiddlerAI(val length: Int = 4,
                val maxDigit: Int = 6) : IRiddler {

    private lateinit var answer: String


    override fun chooseNumber() {
        val result = StringBuilder(length)

        for (i in 0 until length) {
            result.append(randomDigit(maxDigit))
        }

        answer = result.toString()
    }

    override fun check(attempt: String): Pair<Int, Int> {
        return checkAttempt(attempt, answer, length)
    }

    override fun getCorrectAnswer(): String {
        return answer
    }

}