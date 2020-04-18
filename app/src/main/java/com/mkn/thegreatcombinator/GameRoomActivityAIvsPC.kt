package com.mkn.thegreatcombinator

import IRiddler
import ISolver
import RiddlerAI
import SolverPC
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class GameRoomActivityAIvsPC : AppCompatActivity() {

    var attemptCount: Int = 0
    var giveUp: Boolean = false

    fun runSession(riddler: IRiddler, solver: ISolver) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_room_activity_ai_vs_pc)
        val riddler = RiddlerAI()
        val solver = SolverPC()
        //runSession(riddler, solver)

    }
}
