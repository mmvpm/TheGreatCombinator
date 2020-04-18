class SolverPC(override val length: Int = 4,
               override val maxDigit: Int = 6) : ISolver {


    override fun makeAttempt(): String {
        print("Введите число: ")
        var input: String? = readLine()

        if (input == "0000") {
            return "GiveUp"
        }

        while (!Utility.inputValidation(input, length, maxDigit)) {
            print("Неверный формат, попробуйте ещё: ")
            input = readLine()
        }

        return input.toString()
    }

    override fun parseResponse(response: Pair<Int, Int>) {
        println("A: ${response.first}, " +
                "B: ${response.second}")
    }

}