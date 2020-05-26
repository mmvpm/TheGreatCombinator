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
    private val APP_PREFERENCES_LOCALE = "Locale"
    private lateinit var pref: SharedPreferences

    private var locale: String = "en"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        pref = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)
        locale = intent.extras?.getString("locale") ?: "en"
        changeLanguage()
        setButtons()
    }

    override fun onResume() {
        super.onResume()
        changeLanguage()
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

        for (button in listOf(langEn, langRu, langKz)) {
            val convert = mapOf("English" to 1, "Russian" to 2, "Kazakh" to 3)
            val convertShort = mapOf(1 to "en", 2 to "ru", 3 to "kz")
            val textInt: Int = convert[button.text.toString()] ?: 1

            if (textInt == pref.getInt(APP_PREFERENCES_LOCALE, 1)) {
                button.isChecked = true
            }

            button.setOnClickListener {
                pref.edit().remove(APP_PREFERENCES_LOCALE)
                    .putInt(APP_PREFERENCES_LOCALE, textInt).apply()

                locale = convertShort[textInt] ?: "en"
                changeLanguage()
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

    private fun changeLanguage() {
        backButton.text = when (locale) {
            "ru" -> getString(R.string.back_button_ru)
            "kz" -> getString(R.string.back_button_kz)
            else -> getString(R.string.back_button)
        }
        textViewLength.text = when (locale) {
            "ru" -> getString(R.string.length_ru)
            "kz" -> getString(R.string.length_kz)
            else -> getString(R.string.length)
        }
        textViewMaxDigit.text = when (locale) {
            "ru" -> getString(R.string.max_digit_ru)
            "kz" -> getString(R.string.max_digit_kz)
            else -> getString(R.string.max_digit)
        }
        textViewLocale.text = when (locale) {
            "ru" -> getString(R.string.locale_ru)
            "kz" -> getString(R.string.locale_kz)
            else -> getString(R.string.locale)
        }
    }
}
