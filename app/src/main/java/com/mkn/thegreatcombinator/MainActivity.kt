package com.mkn.thegreatcombinator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var settingsLength: Int = 4
    private var settingsMaxDigits: Int = 6
    private var settingsLocale = 1
    private val APP_PREFERENCES_NAME = "Settings"
    private val APP_PREFERENCES_LENGTH = "Length"
    private val APP_PREFERENCES_MAX_DIGITS = "MaxDigits"
    private val APP_PREFERENCES_LOCALE = "Locale"
    private lateinit var pref: SharedPreferences

    private val convert = mapOf(1 to "en", 2 to "ru", 3 to "kz")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)

        changeLanguage()

        toActivityRiddlerAI.setOnClickListener {
            checkSettings()
            val intent = Intent(this, GameActivityRiddlerAI::class.java)
            val b = Bundle()
            b.putInt("maxDigits", settingsMaxDigits)
            b.putInt("length", settingsLength)
            b.putString("locale", convert[settingsLocale])
            intent.putExtras(b)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        toActivitySolverAI.setOnClickListener {
            checkSettings()
            val intent = Intent(this, GameActivitySolverAI::class.java)
            val b = Bundle()
            b.putInt("maxDigits", settingsMaxDigits)
            b.putInt("length", settingsLength)
            b.putString("locale", convert[settingsLocale])
            intent.putExtras(b)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            val b = Bundle()
            b.putString("locale", convert[settingsLocale])
            intent.putExtras(b)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        infoButton.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            val b = Bundle()
            b.putString("locale", convert[settingsLocale])
            intent.putExtras(b)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

    }

    override fun onResume() {
        super.onResume()
        changeLanguage()
    }

    private fun checkSettings() {
        settingsLength = pref.getInt(APP_PREFERENCES_LENGTH, 4)
        settingsMaxDigits = pref.getInt(APP_PREFERENCES_MAX_DIGITS, 6)
        settingsLocale = pref.getInt(APP_PREFERENCES_LOCALE, 1)
    }

    private fun changeLanguage() {
        checkSettings()
        toActivityRiddlerAI.text = when (settingsLocale) {
            2 -> getString(R.string.riddler_AI_ru)
            3-> getString(R.string.riddler_AI_kz)
            else -> getString(R.string.riddler_AI)
        }
        toActivitySolverAI.text = when (settingsLocale) {
            2 -> getString(R.string.solver_AI_ru)
            3 -> getString(R.string.solver_AI_kz)
            else -> getString(R.string.solver_AI)
        }
    }
}
