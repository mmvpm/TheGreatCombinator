class GameRoom(val riddler: Riddler, val solver: Solver) {

    fun runGame() {
        while (true) {
            val session = GameSession(riddler, solver)
            session.runSession()
            session.showResults()

            println("Ещё раз? ")
            if (readLine()?.toLowerCase() != "да") {
                break
            }
        }
    }

}