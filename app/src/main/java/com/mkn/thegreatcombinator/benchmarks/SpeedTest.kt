package com.mkn.thegreatcombinator.benchmarks

import kotlin.math.max
import com.mkn.thegreatcombinator.logic.*


class SpeedTest(private val length: Int,
                private val maxDigit: Int) {

    fun tests(n: Int): Pair<Double, Int> {
        var sum = 0
        var worst = 0
        for (i in 1..n) {
            val cur = run(length, maxDigit)
            sum += cur
            worst = max(worst, cur)
        }
        val average = sum.toDouble() / n
        return Pair(average, worst)
    }

    private fun run(length: Int, maxDigit: Int): Int {
        val riddler = RiddlerAI(length, maxDigit)
        val solver = SolverAI(length, maxDigit)

        riddler.chooseNumber()
        var res = Pair(0, 0)
        var count = 0
        while (res != Pair(length, 0)) {
            count += if (solver.leftAnswers() > 1) 1 else 0
            val att: String = solver.makeAttempt()
            res = riddler.check(att)
            solver.parseResponse(res)
        }
        return count
    }

}