package com.mkn.thegreatcombinator

import org.junit.Test
import org.junit.Assert.*
import com.mkn.thegreatcombinator.logic.*

class AlgorithmTest {

    @Test
    fun solverAI_length4_maxDigit6() {
        val length = 4
        val maxDigit = 6
        val riddler = RiddlerAI(length, maxDigit)
        val solver = SolverAI(length, maxDigit)

        riddler.chooseNumber()
        var res = Pair(0, 0)

        var count = 0
        while (res.first != length) {
            val att: String = solver.makeAttempt()
            res = riddler.check(att)
            solver.parseResponse(res)
            count += 1
            assertTrue(count < 1e4)
        }
    }

    @Test
    fun solverAI_length3_maxDigit9() {
        val length = 3
        val maxDigit = 9
        val riddler = RiddlerAI(length, maxDigit)
        val solver = SolverAI(length, maxDigit)

        riddler.chooseNumber()
        var res = Pair(0, 0)

        var count = 0
        while (res.first != length) {
            val att: String = solver.makeAttempt()
            res = riddler.check(att)
            solver.parseResponse(res)
            count += 1
            assertTrue(count < 1e4)
        }
    }

    @Test
    fun solverAI_length5_maxDigit3() {
        val length = 5
        val maxDigit = 3
        val riddler = RiddlerAI(length, maxDigit)
        val solver = SolverAI(length, maxDigit)

        riddler.chooseNumber()
        var res = Pair(0, 0)

        var count = 0
        while (res.first != length) {
            val att: String = solver.makeAttempt()
            res = riddler.check(att)
            solver.parseResponse(res)
            count += 1
            assertTrue(count < 1e4)
        }
    }

    @Test
    fun solverAI_length7_maxDigit8() {
        val length = 7
        val maxDigit = 8
        val riddler = RiddlerAI(length, maxDigit)
        val solver = SolverAI(length, maxDigit)

        riddler.chooseNumber()
        var res = Pair(0, 0)

        var count = 0
        while (res.first != length) {
            val att: String = solver.makeAttempt()
            res = riddler.check(att)
            solver.parseResponse(res)
            count += 1
            assertTrue(count < 1e6)
        }
    }

    @Test
    fun solverAI_length8_maxDigit9() {
        val length = 8
        val maxDigit = 9
        val riddler = RiddlerAI(length, maxDigit)
        val solver = SolverAI(length, maxDigit)

        riddler.chooseNumber()
        var res = Pair(0, 0)

        var count = 0
        while (res.first != length) {
            val att: String = solver.makeAttempt()
            res = riddler.check(att)
            solver.parseResponse(res)
            count += 1
            assertTrue(count < 1e6)
        }
    }

}
