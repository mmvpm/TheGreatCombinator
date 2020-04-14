fun main() {
    val riddler = RiddlerAI()
    val solver = SolverPC()

    riddler.chooseNumber()
    println("Answer: ${riddler.getCorrectAnswer()}")

    val a: String = solver.makeAttempt()
    solver.parseResponse(riddler.check(a))
}