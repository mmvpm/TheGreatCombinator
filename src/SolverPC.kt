class SolverPC(override val length: Int = 4,
               override val maxDigit: Int = 6) : Solver {


    override fun makeAttempt(): String {
        print("Введите число: ")
        var input: String? = readLine()

        while (!utility.inputValidation(input, length, maxDigit)) {
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