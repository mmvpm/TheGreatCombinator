package com.mkn.thegreatcombinator

import org.junit.Test
import org.junit.Assert.*
import com.mkn.thegreatcombinator.logic.*

class AlgorithmTest {

    @Test
    fun solverAI_length4_maxDigit2() {
        universalTest(4, 2)
    }

    @Test
    fun solverAI_length4_maxDigit3() {
        universalTest(4, 3)
    }

    @Test
    fun solverAI_length4_maxDigit4() {
        universalTest(4, 4)
    }

    @Test
    fun solverAI_length4_maxDigit5() {
        universalTest(4, 5)
    }

    @Test
    fun solverAI_length4_maxDigit6() {
        universalTest(4, 6)
    }

    @Test
    fun solverAI_length4_maxDigit7() {
        universalTest(4, 7)
    }


    @Test
    fun solverAI_length4_maxDigit8() {
        universalTest(4, 8)
    }

    @Test
    fun solverAI_length4_maxDigit9() {
        universalTest(4, 9)
    }

    @Test
    fun solverAI_length5_maxDigit6() {
        universalTest(5, 6)
    }

    @Test
    fun solverAI_length6_maxDigit6() {
        universalTest(6, 6)
    }

    @Test
    fun solverAI_length7_maxDigit6() {
        universalTest(7, 6)
    }

    @Test
    fun solverAI_length8_maxDigit6() {
        universalTest(8, 6)
    }

    @Test
    fun solverAI_length7_maxDigit7() {
        universalTest(7, 7)
    }

    @Test
    fun solverAI_length8_maxDigit8() {
        universalTest(8, 8)
    }

    @Test
    fun solverAI_length8_maxDigit9() {
        universalTest(8, 9)
    }


    private fun universalTest(length: Int, maxDigit: Int) {
        val riddler = RiddlerAI(length, maxDigit)
        val solver = SolverAI(length, maxDigit)

        riddler.chooseNumber()
        val ans = riddler.getCorrectAnswer()
        for (i in ans) {
            val exp = "in 1..$maxDigit"
            val act = "${i - '0'}"
            assertTrue("[i] Expected: $exp, Actual: $act", i - '0' in 1..maxDigit)
        }

        var res = Pair(0, 0)

        var count = 0
        while (res.first != length) {
            val att: String = solver.makeAttempt()
            for (i in att) {
                val exp = "in 1..$maxDigit"
                val act = "${i - '0'}"
                assertTrue("[i] Expected: $exp, Actual: $act", i - '0' in 1..maxDigit)
            }

            res = riddler.check(att)
            val (a, b) = res
            val exp = "in 1..$length"
            assertTrue("[a] Expected: $exp, Actual: $a", a in 0..length)
            assertTrue("[b] Expected: $exp, Actual: $b", b in 0..length)
            assertTrue("[a + b] Expected: $exp, Actual: ${a + b}", (a + b) in 0..length)

            solver.parseResponse(res)
            val act = solver.getLastAttempt()
            assertTrue("[solver.getLastAttempt] Expected: $att, Actual: $act", act == att)

            count += 1
            assertTrue("[count] Expected: < 100, Actual: $count", count < 100)
        }
    }


    @Test
    fun restart_testValidation() {
        val solver = SolverAI()

        solver.makeAttempt()
        solver.parseResponse(Pair(2, 2))
        solver.makeAttempt()
        solver.restart()

        val exp1 = "\"\""
        val act1 = solver.getLastAttempt()
        assertTrue("[solver.getLastAttempt] Expected: $exp1, Actual: $act1", act1 == "")

        val exp2 = Int.MAX_VALUE
        val act2 = solver.leftAnswers()
        assertTrue("[solver.leftAnswers] Expected: $exp2, Actual: $act2", act2 == exp2)
    }

}
