fun main() {
    val riddler = RiddlerAI()
    val solver = SolverPC()

    val room = GameRoom(riddler, solver)
    room.runGame()
}