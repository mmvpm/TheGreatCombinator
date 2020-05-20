package com.mkn.thegreatcombinator

import org.junit.Test
import kotlin.math.max
import com.mkn.thegreatcombinator.logic.*


class SpeedTest {

    @Test
    fun solverAI_standard() {
        val length = 4
        val maxDigit = 6

        val n = 10000
        var sum = 0
        var worst = 0
        for (i in 1..n) {
            val cur = run(length, maxDigit)
            sum += cur
            worst = max(worst, cur)
        }
        val average = sum.toDouble() / n

        println("Length = $length, maxDigit = $maxDigit")
        println("Среднее: $average, Максимальное: $worst")
    }

    @Test
    fun solverAI_bigNum() {
        val length = 6
        val maxDigit = 9

        val n = 100
        var sum = 0
        var worst = 0
        for (i in 1..n) {
            val cur = run(length, maxDigit)
            sum += cur
            worst = max(worst, cur)
        }
        val average = sum.toDouble() / n

        println("Length = $length, maxDigit = $maxDigit")
        println("Среднее: $average, Максимальное: $worst")
    }

    @Test
    fun solverAI_longBinary() {
        val length = 8
        val maxDigit = 2

        val n = 10000
        var sum = 0
        var worst = 0
        for (i in 1..n) {
            val cur = run(length, maxDigit)
            sum += cur
            worst = max(worst, cur)
        }
        val average = sum.toDouble() / n

        println("Length = $length, maxDigit = $maxDigit")
        println("Среднее: $average, Максимальное: $worst")
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