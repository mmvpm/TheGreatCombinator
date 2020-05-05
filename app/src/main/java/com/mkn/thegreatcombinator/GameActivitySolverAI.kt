package com.mkn.thegreatcombinator

import com.mkn.thegreatcombinator.logic.*
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_solver_ai.*

class GameActivitySolverAI : AppCompatActivity() {

    // Количество сделанных попыток
    private var attemptCount: Int = 0
    // True, если число уже загадано
    private var numberMadeUp: Boolean = false

    private var length: Int = 0
    private var maxDigit: Int = 0

    private var solver = SolverAI(length, maxDigit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solver_ai)
        setUpSettings()
        runSession()
    }

    private fun runSession() {
        solver.restart()

        attemptCount = 0
        numberMadeUp = false
        dialogWindow.text = ""
        appendToTextView(getString(R.string.make_number_and_press))

        replyButton.setOnClickListener {
            if (!numberMadeUp) {
                numberMadeUp = true
            }
            else {
                val response: Pair<Int, Int> = readAttempt()

                appendToTextView("   A: ${response.first}   B: ${response.second}")

                if (!checkResponseCorrectness(response)) {
                    appendToTextView(getString(R.string.incorrect_response),
                                     true)
                    printAttempt()
                    return@setOnClickListener
                }
                if (response == Pair(length, 0)) {
                    showSessionResults()
                    return@setOnClickListener
                }
                solver.parseResponse(response)
            }

            solver.makeAttempt()
            attemptCount += 1
            printAttempt()
        }

        setEnabledInterface(true)
    }

    private fun setEnabledInterface(status: Boolean) {
        replyButton.isEnabled = status
        leftPlus.isEnabled = status
        rightPlus.isEnabled = status
        leftMinus.isEnabled = status
        rightMinus.isEnabled = status
    }

    private fun setUpSettings() {
        length = intent.extras?.getInt("length") ?: 4
        maxDigit = intent.extras?.getInt("maxDigits") ?: 6

        solver = SolverAI(length, maxDigit)

        dialogWindow.movementMethod = ScrollingMovementMethod()
        backButton.setOnClickListener {
            finish()
        }

        restartButton.setOnClickListener {
            runSession()
        }

        leftPlus.setOnClickListener {
            leftCounter.text = incZeroBased(leftCounter.text.toString(), length + 1)
        }
        rightPlus.setOnClickListener {
            rightCounter.text = incZeroBased(rightCounter.text.toString(), length + 1)
        }

        leftMinus.setOnClickListener {
            leftCounter.text = decZeroBased(leftCounter.text.toString(), length + 1)
        }
        rightMinus.setOnClickListener {
            rightCounter.text = decZeroBased(rightCounter.text.toString(), length + 1)
        }
    }

    private fun showSessionResults() {
        appendToTextView(getString(R.string.AI_win), true)
        setEnabledInterface(false)
    }

    private fun appendToTextView(message: String, startWithNewLine: Boolean = false) {
        val newText: String = (if (startWithNewLine) "\n" else "") + message
        dialogWindow.append(newText)
    }

    private fun checkResponseCorrectness(response: Pair<Int, Int>): Boolean
        = (response.toList().sum() <= length)

    private fun readAttempt(): Pair<Int, Int> {
        val aCount: Int = leftCounter.text.toString().toInt()
        val bCount: Int = rightCounter.text.toString().toInt()
        return Pair(aCount, bCount)
    }

    private fun printAttempt() {
        appendToTextView("${attemptCount}. ${solver.getLastAttempt()} ?",
                         true)
    }
}
