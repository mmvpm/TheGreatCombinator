class RiddlerPC(override val length: Int = 4,
                override val maxDigit: Int = 6) : Riddler {

    private fun inputValidation(input: String?): Boolean {
        if (input == null || input.length != length) {
            return false
        }
        for (i in 0 until length) {
            if (input[i] !in '1'..('0' + maxDigit)) {
                return false
            }
        }
        return true
    }

    override fun chooseNumber() {
        print("Загадайте число и нажмите Enter: ")
        readLine()
    }

    override fun check(attempt: String): Pair<Int, Int> {
        println("Думаю, ты загадал ${attempt}")

        print("Сколько цифр совпало в точности? A: ")
        val aCount: Int = readLine()!!.toInt()

        print("Сколько есть, но не на своём месте? B: ")
        val bCount: Int = readLine()!!.toInt()

        return Pair(aCount, bCount)
    }

    override fun getCorrectAnswer(): String {
        print("Введите правильный ответ: ")
        var answer: String? = readLine()

        while (!inputValidation(answer)) {
            print("Неверный формат, попробуйте ещё: ")
            answer = readLine()
        }

        return answer.toString()
    }

}