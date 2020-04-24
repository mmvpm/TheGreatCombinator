interface IRiddler {
fun chooseNumber()
    fun getCorrectAnswer(): String
    fun check(attempt: String): Pair<Int, Int>
}