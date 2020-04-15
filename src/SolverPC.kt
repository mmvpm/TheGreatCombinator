class SolverPC(override val length: Int = 4,
               override val maxDigit: Int = 6) : Solver {

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

    override fun makeAttempt(): String {
        print("Введите число: ")
        var input: String? = readLine()

        while (!inputValidation(input)) {
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