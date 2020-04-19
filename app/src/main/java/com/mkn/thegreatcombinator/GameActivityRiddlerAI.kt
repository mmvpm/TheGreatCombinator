package com.mkn.thegreatcombinator

import RiddlerAI
import Utility
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_riddler_ai.*


class GameActivityRiddlerAI : AppCompatActivity() {

    var attemptCount: Int = 0
    var giveUp: Boolean = false

    var length: Int = 4 // TODO: from MainActivity
    var maxDigit: Int = 6 // TODO: from MainActivity

    val riddler = RiddlerAI(length, maxDigit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riddler_ai)

        runSession() // TODO: while
    }

    private fun runSession() {
        riddler.chooseNumber()
        appendToTextView("Число загадано") // TODO: to strings.xml

        editText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER) { // TODO: Ввод с кнопок

                val attempt: String = editText.text.toString()

                // TODO: Кнопка сдаться
                if (attempt == "0000") {
                    giveUp = true
                    showSessionResults()
                }
                if (Utility.inputValidation(attempt, length, maxDigit)) {
                    val response = riddler.check(attempt)
                    attemptCount += 1
                    parseResponse(attempt, response)
                }
                true
            }
            else {
                false
            }
        }
    }

    private fun showSessionResults() {
        if (!giveUp) {
            appendToTextView("Верно!") // TODO: to strings.xml
        }
        else {
            appendToTextView("Вы сдались. Ответ: ${riddler.getCorrectAnswer()}") // TODO: to strings.xml
        }

        editText.setText("")
        editText.isCursorVisible = false
        editText.isFocusable = false
        editText.isLongClickable = false

        // TODO: кнопка restart
    }


    // TODO: Scroll View
    private fun appendToTextView(message: String) {
        var newText: String = textView.text.toString() + message + '\n'

        if (newText.count { it == '\n'} > 9) { // TODO: magic const
            val prefix: Int = newText.indexOfFirst { it == '\n' }
            newText = newText.drop(prefix + 1)
        }

        textView.text = newText
    }

    private fun parseResponse(attempt: String, response: Pair<Int, Int>) {
        val message = "${attemptCount}. $attempt  -  A: ${response.first}, B: ${response.second}"
        appendToTextView(message)

        if (response == Pair(length, 0)) {
            showSessionResults()
        }
    }
}