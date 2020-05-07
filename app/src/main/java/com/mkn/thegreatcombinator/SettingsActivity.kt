package com.mkn.thegreatcombinator

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    val APP_PREFERENCES_NAME = "Settings"
    val APP_PREFERENCES_LENGTH = "Length"
    val APP_PREFERENCES_MAX_DIGITS = "MaxDigits"
    lateinit var pref: SharedPreferences


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

            button.setOnClickListener{
                val editor = pref.edit()
                editor.remove(APP_PREFERENCES_LENGTH)
                editor.putInt(APP_PREFERENCES_LENGTH, button.text.toString().toInt())
                editor.apply()
            }
        }

        for (i in 0 until (mDigGroup.childCount)) {
            val button: RadioButton = mDigGroup.getChildAt(i) as RadioButton

            if (button.text.toString().toInt() == pref.getInt(APP_PREFERENCES_MAX_DIGITS, 6)) {
                button.isChecked = true
            }

            button.setOnClickListener{
                val editor = pref.edit()
                editor.remove(APP_PREFERENCES_MAX_DIGITS)
                editor.putInt(APP_PREFERENCES_MAX_DIGITS, button.text.toString().toInt())
                editor.apply()
            }
        }
    }

}
