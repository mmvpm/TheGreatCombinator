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
    private val APP_PREFERENCES_NAME = "Settings"
    private val APP_PREFERENCES_LENGTH = "Length"
    private val APP_PREFERENCES_MAX_DIGITS = "MaxDigits"
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)

        toActivityRiddlerAI.setOnClickListener {
            checkSettings()
            val intent = Intent(this, GameActivityRiddlerAI::class.java)
            val b = Bundle()
            b.putInt("maxDigits", settingsMaxDigits)
            b.putInt("length", settingsLength)
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
            intent.putExtras(b)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        infoButton.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    private fun checkSettings() {
        settingsLength = pref.getInt(APP_PREFERENCES_LENGTH, 4)
        settingsMaxDigits = pref.getInt(APP_PREFERENCES_MAX_DIGITS, 6)
    }
}
