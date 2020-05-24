package com.mkn.thegreatcombinator

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    private val APP_PREFERENCES_NAME = "Settings"
    private val APP_PREFERENCES_LENGTH = "Length"
    private val APP_PREFERENCES_MAX_DIGITS = "MaxDigits"
    private lateinit var pref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        pref = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)
        setButtons()


    }

    private fun setButtons() {
        for (i in 0 until (lenGroup.childCount)) {
            val button: RadioButton = lenGroup.getChildAt(i) as RadioButton

            if (button.text.toString().toInt() == pref.getInt(APP_PREFERENCES_LENGTH, 4)) {
                button.isChecked = true
            }

            button.setOnClickListener {
                pref.edit().remove(APP_PREFERENCES_LENGTH)
                    .putInt(APP_PREFERENCES_LENGTH, button.text.toString().toInt()).apply()
            }
        }

        for (i in 0 until (mDigGroup.childCount)) {
            val button: RadioButton = mDigGroup.getChildAt(i) as RadioButton

            if (button.text.toString().toInt() == pref.getInt(APP_PREFERENCES_MAX_DIGITS, 6)) {
                button.isChecked = true
            }

            button.setOnClickListener {
                pref.edit().remove(APP_PREFERENCES_MAX_DIGITS)
                    .putInt(APP_PREFERENCES_MAX_DIGITS, button.text.toString().toInt()).apply()
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
