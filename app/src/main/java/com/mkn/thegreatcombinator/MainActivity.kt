package com.mkn.thegreatcombinator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var settingsLength: Int = 4
    var settingsMaxDigits: Int = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toActivityRiddlerAI.setOnClickListener {
            val intent = Intent(this, GameActivityRiddlerAI::class.java)
            val b = Bundle()
            b.putInt("maxDigits", settingsMaxDigits)
            b.putInt("length", settingsLength)
            intent.putExtras(b)
            startActivity(intent)
        }

        toActivitySolverAI.setOnClickListener {
            val intent = Intent(this, GameActivitySolverAI::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        infoButton.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }
}
