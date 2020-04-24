interface ISolver {
    fun makeAttempt(): String
    fun parseResponse(response: Pair<Int, Int>)
}