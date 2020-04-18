package com.mkn.thegreatcombinator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        first_choice.setOnClickListener {
            val intent = Intent(this, GameRoomActivityAIvsPC::class.java)
            startActivity(intent)
        }

    }
}
