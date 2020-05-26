package com.mkn.thegreatcombinator

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    private var locale: String = "en"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        backButton.setOnClickListener {
            finish()
        }
        rulesWindow.movementMethod = ScrollingMovementMethod()
        locale = intent.extras?.getString("locale") ?: "en"
        changeLanguage()
    }

    override fun onResume() {
        super.onResume()
        changeLanguage()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun changeLanguage() {
        backButton.text = when (locale) {
            "ru" -> getString(R.string.back_button_ru)
            "kz" -> getString(R.string.back_button_kz)
            else -> getString(R.string.back_button)
        }
        textViewHelp.text = when (locale) {
            "ru" -> getString(R.string.help_ru)
            "kz" -> getString(R.string.help_kz)
            else -> getString(R.string.help)
        }
        rulesWindow.text = when (locale) {
            "ru" -> getString(R.string.rules_ru)
            "kz" -> getString(R.string.rules_ru)
            else -> getString(R.string.rules)
        }
    }

}
