class GameSession(val riddler: IRiddler, val solver: ISolver) {

    var attemptCount: Int = 0
    var giveUp: Boolean = false

    fun runSession() {
        riddler.chooseNumber()
        var response: Pair<Int, Int> = Pair(0, 0)

        while (response.first != riddler.length) {
            val attempt: String = solver.makeAttempt()
            attemptCount += 1

            if (attempt == "GiveUp") {
                giveUp = true
                break
            }

            response = riddler.check(attempt)
            solver.parseResponse(response)
        }
    }

    fun showResults() {
        if (!giveUp) {
            println("Число отгадано за ${attemptCount} попыток")
        }
        else {
            println("Отгадывающий сдался после ${attemptCount} попыток")
            println("Было загадано число ${riddler.getCorrectAnswer()}")
        }
    }
}