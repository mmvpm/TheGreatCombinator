package com.mkn.thegreatcombinator

import android.graphics.drawable.Drawable
import com.mkn.thegreatcombinator.logic.*
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import kotlinx.android.synthetic.main.activity_riddler_ai.*


class GameActivityRiddlerAI : AppCompatActivity() {

    // Количество сделанных попыток
    private var attemptCount: Int = 0
    // True, если пользователь сдался
    private var giveUp: Boolean = false

    private var length: Int = 4
    private var maxDigit: Int = 6
    private var locale: String = "en"

    private var riddler = RiddlerAI(length, maxDigit)

    // Массивы кнопок, генерирующихся автоматически
    private var counterHolder = ArrayList<TextView>()
    private var plusHolder = ArrayList<TextView>()
    private var minusHolder = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riddler_ai)
        changeLanguage()
        setUpSettings()
        runSession()
    }

    override fun onResume() {
        super.onResume()
        changeLanguage()
    }

    private fun runSession() {
        riddler.chooseNumber()

        giveUp = false
        attemptCount = 0
        dialogWindow.text = ""
        appendToTextView(getRightString("number_made_up"))

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
        locale = intent.extras?.getString("locale") ?: "en"
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
            newPlus.setBackgroundResource(R.drawable.plus_minus_button)
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
            newMinus.setBackgroundResource(R.drawable.plus_minus_button)
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
            appendToTextView(getRightString("you_win"), true)
        } else {
            appendToTextView("${getRightString("you_gave_up")} ${riddler.getCorrectAnswer()}", true)
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


    private val convert = HashMap<String, Int>()

    private fun getRightString(input: String): String {
        return getString(convert[input + "_$locale"] ?: 0)
    }

    private fun changeLanguage() {
        convert["back_button_en"] = R.string.back_button
        convert["back_button_ru"] = R.string.back_button_ru
        convert["back_button_kz"] = R.string.back_button_kz

        convert["restart_button_en"] = R.string.restart_button
        convert["restart_button_ru"] = R.string.restart_button_ru
        convert["restart_button_kz"] = R.string.restart_button_kz

        convert["give_up_button_en"] = R.string.give_up_button
        convert["give_up_button_ru"] = R.string.give_up_button_ru
        convert["give_up_button_kz"] = R.string.give_up_button_kz

        convert["check_button_en"] = R.string.check_button
        convert["check_button_ru"] = R.string.check_button_ru
        convert["check_button_kz"] = R.string.check_button_kz

        convert["number_made_up_en"] = R.string.number_made_up
        convert["number_made_up_ru"] = R.string.number_made_up_ru
        convert["number_made_up_kz"] = R.string.number_made_up_kz

        convert["you_win_en"] = R.string.you_win
        convert["you_win_ru"] = R.string.you_win_ru
        convert["you_win_kz"] = R.string.you_win_kz

        convert["you_gave_up_en"] = R.string.you_gave_up
        convert["you_gave_up_ru"] = R.string.you_gave_up_ru
        convert["you_gave_up_kz"] = R.string.you_gave_up_kz

        convert["incorrect_response_en"] = R.string.incorrect_response
        convert["incorrect_response_ru"] = R.string.incorrect_response_ru
        convert["incorrect_response_kz"] = R.string.incorrect_response_kz

        backButton.text = getRightString("back_button")
        restartButton.text = getRightString("restart_button")
        giveUpButton.text = getRightString("give_up_button")
        sendAttemptButton.text = getRightString("check_button")
    }
}