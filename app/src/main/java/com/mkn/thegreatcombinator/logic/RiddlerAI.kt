package com.mkn.thegreatcombinator.logic

class RiddlerAI(private val length: Int = 4,
                private val maxDigit: Int = 6) : IRiddler {

    private var answer: String = ""


    override fun chooseNumber() {
        // Загадывание нового числа
        answer = buildString {
            for (i in 0 until this@RiddlerAI.length) {
                append(randomDigit(maxDigit))
            }
        }
    }

    override fun check(attempt: String): Pair<Int, Int>
        = checkAttempt(attempt, answer, length)

    override fun getCorrectAnswer(): String = answer

}