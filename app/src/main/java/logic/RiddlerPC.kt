class RiddlerPC(override val length: Int = 4,
                override val maxDigit: Int = 6) : IRiddler {


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

        while (!Utility.inputValidation(answer, length, maxDigit)) {
            print("Неверный формат, попробуйте ещё: ")
            answer = readLine()
        }

        return answer.toString()
    }

}