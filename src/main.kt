fun main() {
//    val riddler = RiddlerAI()
//    val solver = SolverPC()

    val riddler = RiddlerPC()
    val solver = SolverAI()

    val room = GameRoom(riddler, solver)
    room.runGame()
}