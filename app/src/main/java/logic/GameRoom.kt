class GameRoom(val riddler: IRiddler, val solver: ISolver) {

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