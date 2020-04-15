class GameSession(val riddler: Riddler, val solver: Solver) {

    var attemptCount: Int = 0

    fun runSession() {
        riddler.chooseNumber()

        var response: Pair<Int, Int> = Pair(0, 0)

        while (response.first != riddler.length) {
            val attempt: String = solver.makeAttempt()
            attemptCount += 1

            response = riddler.check(attempt)
            solver.parseResponse(response)
        }
    }

    fun showResults() {
        println("Число отгадано за ${attemptCount} попыток")
    }
}