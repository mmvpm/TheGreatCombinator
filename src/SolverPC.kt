class SolverPC(override val length: Int = 4,
               override val maxDigit: Int = 6) : Solver {

    fun inputValidation(input: String?): Boolean {
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
        print("Make your guess: ")
        var input: String? = readLine()

        while (!inputValidation(input)) {
            print("Incorrect guess, try again: ")
            input = readLine()
        }

        return input.toString()
    }

    override fun parseResponse(response: Pair<Int, Int>) {
        val aCount: Int = response.first
        val bCount: Int = response.second

        println("A: ${aCount}, B: ${bCount}")
    }

}