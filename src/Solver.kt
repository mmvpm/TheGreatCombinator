interface Solver {
    val length: Int
    val maxDigit: Int

    fun makeAttempt(): String
    fun parseResponse(response: Pair<Int, Int>)
}