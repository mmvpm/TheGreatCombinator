package com.mkn.thegreatcombinator

import com.mkn.thegreatcombinator.logic.*
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_riddler_ai.*


class GameActivityRiddlerAI : AppCompatActivity() {

    // Количество сделанных попыток
    private var attemptCount: Int = 0
    // True, если пользователь сдался
    private var giveUp: Boolean = false

    private var length: Int = 4
    private var maxDigit: Int = 6

    private var riddler = RiddlerAI(length, maxDigit)

    // Массивы кнопок, генерирующихся автоматически
    private var counterHolder = ArrayList<TextView>()
    private var plusHolder = ArrayList<TextView>()
    private var minusHolder = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riddler_ai)
        setUpSettings()
        runSession()
    }

    private fun runSession() {
        riddler.chooseNumber()

        giveUp = false
        attemptCount = 0
        dialogWindow.text = ""
        appendToTextView(getString(R.string.number_made_up))

        giveUpButton.setOnClickListener {
            giveUp = true
            showSessionResults()
        }
        sendAttemptButton.setOnClickListener {
            val attempt: String = readAttempt()

            val response = riddler.check(attempt)
            attemptCount += 1
            parseResponse(attempt, response)
        }
        setEnabledInterface(true)
    }

    private fun setEnabledInterface(status: Boolean) {
        sendAttemptButton.isEnabled = status
        giveUpButton.isEnabled = status
        plusHolder.forEach { it.isEnabled = status }
        minusHolder.forEach { it.isEnabled = status }
    }

    private fun setUpSettings() {
        length = intent.extras?.getInt("length") ?: 4
        maxDigit = intent.extras?.getInt("maxDigits") ?: 6
        riddler = RiddlerAI(length, maxDigit)

        gameInterfaceMaker()

        dialogWindow.movementMethod = ScrollingMovementMethod()
        backButton.setOnClickListener {
            finish()
        }

        restartButton.setOnClickListener {
            runSession()
        }
    }

    private fun gameInterfaceMaker() {
        for (id in 1..length) {
            val newCounter = TextView(this)
            newCounter.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newCounter.gravity = Gravity.CENTER
            newCounter.setBackgroundColor(getColor(android.R.color.transparent))
            newCounter.setTextColor(getColor(R.color.digits_riddler_AI))
            newCounter.textSize = 40F
            newCounter.text = getString(R.string.start_digit_riddler_AI)
            counterHolder.add(newCounter)

            val newPlus = Button(this)
            newPlus.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newPlus.setBackgroundColor(getColor(android.R.color.transparent))
            newPlus.setTextColor(getColor(R.color.digits_riddler_AI))
            newPlus.textSize = 30F
            newPlus.text = getString(R.string.plus)
            newPlus.setOnClickListener {
                newCounter.text = incOneBased(newCounter.text.toString(), maxDigit)
            }
            plusHolder.add(newPlus)

            val newMinus = Button(this)
            newMinus.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newMinus.setBackgroundColor(getColor(android.R.color.transparent))
            newMinus.setTextColor(getColor(R.color.digits_riddler_AI))
            newMinus.textSize = 30F
            newMinus.text = getString(R.string.minus)
            newMinus.setOnClickListener {
                newCounter.text = decOneBased(newCounter.text.toString(), maxDigit)
            }
            minusHolder.add(newMinus)

            plusLayout.addView(newPlus)
            counterLayout.addView(newCounter)
            minusLayout.addView(newMinus)
        }
    }

    private fun showSessionResults() {
        if (!giveUp) {
            appendToTextView(getString(R.string.you_win), true)
        } else {
            appendToTextView("${getString(R.string.you_gave_up)} ${riddler.getCorrectAnswer()}",
                             true)
        }
        setEnabledInterface(false)
    }

    private fun appendToTextView(message: String, startWithNewLine: Boolean = false) {
        val newText: String = (if (startWithNewLine) "\n" else "") + message
        dialogWindow.append(newText)
    }

    private fun parseResponse(attempt: String, response: Pair<Int, Int>) {
        val message = "${attemptCount}. $attempt   A: ${response.first}   B: ${response.second}"
        appendToTextView(message, true)

        if (response == Pair(length, 0)) {
            showSessionResults()
        }
    }

    private fun readAttempt(): String {
        var attemptNumber = ""
        for (counter in counterHolder) {
            attemptNumber += counter.text.toString()
        }
        return attemptNumber
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}