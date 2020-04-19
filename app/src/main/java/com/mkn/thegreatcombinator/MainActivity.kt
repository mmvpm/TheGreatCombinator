package com.mkn.thegreatcombinator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toActivityRiddlerAI.setOnClickListener {
            val intent = Intent(this, GameActivityRiddlerAI::class.java)
            startActivity(intent)
        }

        toActivitySolverAI.setOnClickListener {
            val intent = Intent(this, GameActivitySolverAI::class.java)
            startActivity(intent)
        }

    }
}
