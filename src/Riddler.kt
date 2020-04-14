interface Riddler {
    val length: Int
    val maxDigit: Int

    fun chooseNumber()
    fun getCorrectAnswer(): String
    fun check(attempt: String): Pair<Int, Int>
}