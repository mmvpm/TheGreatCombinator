class RiddlerAI(override val length: Int = 4,
                override val maxDigit: Int = 6) : IRiddler {

    private lateinit var answer: String


    override fun chooseNumber() {
        val result = StringBuilder(length)

        for (i in 0 until length) {
            result.append(Utility.randomDigit(maxDigit))
        }

        answer = result.toString()
    }

    override fun check(attempt: String): Pair<Int, Int> {
        return Utility.check(attempt, answer, length)
    }

    override fun getCorrectAnswer(): String {
        return answer
    }

}