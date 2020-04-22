package com.mkn.thegreatcombinator

import RiddlerAI
import Utility
import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_riddler_ai.*
import com.mkn.thegreatcombinator.R.color.buttonColor as buttonColor1


class GameActivityRiddlerAI : AppCompatActivity() {
    var attemptCount: Int = 0
    var giveUp: Boolean = false

    var length: Int = 0
    var maxDigit: Int = 0

    var riddler = RiddlerAI(length, maxDigit)
    var counterHolder = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riddler_ai)
        setUpSettings()

        runSession()
    }

    private fun disableInterface(){
        sendAttemptButton.setOnClickListener(null)
        giveUpButton.setOnClickListener(null)
        sendAttemptButton.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.disableButtonColor, null))
        giveUpButton.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.disableButtonColor, null))
    }

    private fun runSession() {
        riddler.chooseNumber()
        dialogWindow.text = ""
        appendToTextView("Number made up") // TODO: to strings.xml

        giveUpButton.setOnClickListener{
            giveUp = true
            showSessionResults()
        }

        sendAttemptButton.setOnClickListener{
            val attempt: String = readAttempt()

            if (Utility.inputValidation(attempt, length, maxDigit)) {
                val response = riddler.check(attempt)
                attemptCount += 1
                parseResponse(attempt, response)
            }
        }

        sendAttemptButton.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
        giveUpButton.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
    }


    private fun setUpSettings() {
        length = intent.extras?.getInt("length") ?: 4
        maxDigit = intent.extras?.getInt("maxDigits") ?: 6
        riddler = RiddlerAI(length, maxDigit)
        gameInterfaceMaker()

        dialogWindow.movementMethod = ScrollingMovementMethod() // Scroll
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
            newCounter.text = "1"
            newCounter.textSize = 70F
            counterHolder.add(newCounter)

            val newButton = Button(this)
            newButton.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newButton.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
            newButton.setTextColor(ResourcesCompat.getColor(resources, R.color.buttonTextColor, null))
            newButton.textSize = 30F
            newButton.text = "+"
            newButton.setOnClickListener {
                newCounter.text = newCounter.text.toString().toInt().rem(maxDigit).inc().toString()
            }

            val newButtonDecr = Button(this)
            newButtonDecr.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1F
            )
            newButtonDecr.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.buttonColor, null))
            newButtonDecr.setTextColor(ResourcesCompat.getColor(resources, R.color.buttonTextColor, null))
            newButtonDecr.textSize = 30F
            newButtonDecr.text = "-"
            newButtonDecr.setOnClickListener {
                newCounter.text =
                    if (newCounter.text.toString().toInt() == 1) maxDigit.toString()
                    else newCounter.text.toString().toInt().dec().toString()
            }

            plusLayout.addView(newButton)
            counterLayout.addView(newCounter)
            minusLayout.addView(newButtonDecr)
        }
    }

    private fun showSessionResults() {
        if (!giveUp) {
            appendToTextView("You win!\n If you want to play again, click Restart button") // TODO: to strings.xml
        } else {
            appendToTextView("You gave up. Answer: ${riddler.getCorrectAnswer()}") // TODO: to strings.xml
        }
        disableInterface()

    }


    private fun appendToTextView(message: String) {
        var newText: String = message + '\n'

        if (newText.count { it == '\n' } > 9) { // TODO: magic const
            val prefix: Int = newText.indexOfFirst { it == '\n' }
            newText = newText.drop(prefix + 1)
        }

        dialogWindow.append(newText)
    }

    private fun parseResponse(attempt: String, response: Pair<Int, Int>) {
        val message = "${attemptCount}. $attempt  -  A: ${response.first}, B: ${response.second}"
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