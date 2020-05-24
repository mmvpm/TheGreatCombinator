package com.mkn.thegreatcombinator

import com.mkn.thegreatcombinator.logic.*
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_riddler_ai.*
import kotlinx.android.synthetic.main.activity_solver_ai.*
import kotlinx.android.synthetic.main.activity_solver_ai.backButton
import kotlinx.android.synthetic.main.activity_solver_ai.counterLayout
import kotlinx.android.synthetic.main.activity_solver_ai.dialogWindow
import kotlinx.android.synthetic.main.activity_solver_ai.minusLayout
import kotlinx.android.synthetic.main.activity_solver_ai.plusLayout
import kotlinx.android.synthetic.main.activity_solver_ai.restartButton
import org.w3c.dom.Text

class GameActivitySolverAI : AppCompatActivity() {

    // Количество сделанных попыток
    private var attemptCount: Int = 0
    // True, если число уже загадано
    private var numberMadeUp: Boolean = false

    private var length: Int = 0
    private var maxDigit: Int = 0

    private var solver = SolverAI(length, maxDigit)

    private lateinit var leftPlus: Button
    private lateinit var rightPlus: Button
    private lateinit var leftMinus: Button
    private lateinit var rightMinus: Button
    private lateinit var rightCounter: TextView
    private lateinit var leftCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solver_ai)
        gameInterfaceMaker()
        setUpSettings()
        runSession()
    }

    private fun gameInterfaceMaker() {
        for (id in 1..2) {
            val newCounter = TextView(this)
            newCounter.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newCounter.gravity = Gravity.CENTER
            newCounter.setBackgroundColor(getColor(android.R.color.transparent))
            newCounter.setTextColor(getColor(R.color.digits_solver_AI))
            newCounter.textSize = 40F
            newCounter.text = getString(R.string.start_digit_solver_AI)
            if (id == 1) leftCounter = newCounter else rightCounter = newCounter

            val newPlus = Button(this)
            newPlus.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newPlus.setBackgroundResource(R.drawable.plus_minus_button)
            newPlus.setTextColor(getColor(R.color.digits_riddler_AI))
            newPlus.textSize = 30F
            newPlus.text = getString(R.string.plus)
            if (id == 1) leftPlus = newPlus else rightPlus = newPlus

            val newMinus = Button(this)
            newMinus.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newMinus.setBackgroundResource(R.drawable.plus_minus_button)
            newMinus.setTextColor(getColor(R.color.digits_riddler_AI))
            newMinus.textSize = 30F
            newMinus.text = getString(R.string.minus)
            if (id == 1) leftMinus = newMinus else rightMinus = newMinus

            plusLayout.addView(newPlus)
            counterLayout.addView(newCounter)
            minusLayout.addView(newMinus)
        }
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
                if (!solver.parseResponse(response)) {
                    appendToTextView(getString(R.string.conflicting_data),
                        true)
                    setEnabledInterface(false)
                    return@setOnClickListener
                }
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

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
