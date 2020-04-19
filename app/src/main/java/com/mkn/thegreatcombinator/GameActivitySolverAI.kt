package com.mkn.thegreatcombinator

import SolverAI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import kotlinx.android.synthetic.main.activity_solver_ai.*
import kotlinx.android.synthetic.main.activity_solver_ai.editText

class GameActivitySolverAI : AppCompatActivity() {

    var attemptCount: Int = 0

    var length: Int = 4 // TODO: from MainActivity
    var maxDigit: Int = 6 // TODO: from MainActivity

    val solver = SolverAI(length, maxDigit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solver_ai)

        runSession() // TODO: while
    }

    private fun runSession() {
        chooseNumber()

        var attempt: String = solver.makeAttempt()
        attemptCount += 1
        appendToTextView("${attemptCount}. ${attempt} ?")

        editText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_ENTER) {

                val input: String = editText.text.toString()

                if (input.isNotEmpty()) { // TODO: Ввод с кнопок
                    val response = Pair((input[0] - '0'), (input[2] - '0'))

                    if (response == Pair(length, 0)) {
                        showSessionResults()
                        return@setOnKeyListener true
                    }

                    editText.setText("")
                    solver.parseResponse(response)

                    attempt = solver.makeAttempt()
                    attemptCount += 1
                    appendToTextView("${attemptCount}. ${attempt} ?")
                }

                true
            }
            else {
                false
            }
        }
    }

    private fun showSessionResults() {
        appendToTextView("Я угадал!") // TODO: to strings.xml

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

    private fun chooseNumber() {
        appendToTextView("Загадайте число") // TODO: to strings.xml
        // TODO: кнопка "Загадал"
    }
}
