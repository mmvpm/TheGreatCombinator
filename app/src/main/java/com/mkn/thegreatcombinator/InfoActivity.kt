package com.mkn.thegreatcombinator

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_info.backButton
import kotlinx.android.synthetic.main.activity_riddler_ai.*
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.InputStream

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        backButton.setOnClickListener {
            finish()
        }
        rulesWindow.movementMethod = ScrollingMovementMethod()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}
