package com.mkn.thegreatcombinator

import RiddlerAI
import Utility
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_riddler_ai.*


class GameActivityRiddlerAI : AppCompatActivity() {
    var attemptCount: Int = 0
    var giveUp: Boolean = false

    var length: Int = 0
    var maxDigit: Int = 0

    var riddler = RiddlerAI(length, maxDigit)
    var counterHolder = ArrayList<TextView>()
    var plusHolder = ArrayList<TextView>()
    var minusHolder = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riddler_ai)
        setUpSettings()
        runSession()
    }

    private fun runSession() {
        riddler.chooseNumber()

        dialogWindow.text = ""
        appendToTextView(getString(R.string.NumberMadeUp))

        giveUpButton.setOnClickListener {
            giveUp = true
            showSessionResults()
        }
        sendAttemptButton.setOnClickListener {
            val attempt: String = readAttempt()

            if (Utility.inputValidation(attempt, length, maxDigit)) {
                val response = riddler.check(attempt)
                attemptCount += 1
                parseResponse(attempt, response)
            }
        }
        setEnbledInterface(true)
    }

    private fun setEnbledInterface(status: Boolean) {
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
            attemptCount = 0
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
            newCounter.setTextColor(getColor(R.color.digitsRiddlerAI))
            newCounter.textSize = 40F
            newCounter.text = getString(R.string.StartDigit)
            counterHolder.add(newCounter)

            val newPlus = Button(this)
            newPlus.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newPlus.setBackgroundColor(0);
            newPlus.setTextColor(getColor(R.color.digitsRiddlerAI))
            newPlus.textSize = 30F
            newPlus.text = getString(R.string.Plus)
            newPlus.setOnClickListener {
                newCounter.text = newCounter.text.toString().toInt().rem(maxDigit).inc().toString()
            }
            plusHolder.add(newPlus)

            val newMinus = Button(this)
            newMinus.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newMinus.setBackgroundColor(0);
            newMinus.setTextColor(getColor(R.color.digitsRiddlerAI))
            newMinus.textSize = 30F
            newMinus.text = getString(R.string.Minus)
            newMinus.setOnClickListener {
                newCounter.text =
                    if (newCounter.text.toString().toInt() == 1) maxDigit.toString()
                    else newCounter.text.toString().toInt().dec().toString()
            }
            minusHolder.add(newMinus)

            plusLayout.addView(newPlus)
            counterLayout.addView(newCounter)
            minusLayout.addView(newMinus)
        }
    }

    private fun showSessionResults() {
        if (!giveUp) {
            appendToTextView(getString(R.string.YouWin))
        } else {
            appendToTextView("${getString(R.string.YouGaveUp)} ${riddler.getCorrectAnswer()}")
        }
        setEnbledInterface(false)
    }

    private fun appendToTextView(message: String) {
        val newText: String = message + '\n'
        dialogWindow.append(newText)
    }

    private fun parseResponse(attempt: String, response: Pair<Int, Int>) {
        val message = "${attemptCount}. $attempt   A: ${response.first}   B: ${response.second}"
        appendToTextView(message)

        if (response == Pair(length, 0)) {
            showSessionResults()
        }
    }

    private fun readAttempt() : String {
        var attemptNumber = ""
        for(counter in counterHolder){
            attemptNumber += counter.text.toString()
        }
        return attemptNumber
    }
}