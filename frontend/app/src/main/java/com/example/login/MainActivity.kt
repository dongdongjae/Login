package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.signin_intent_button).apply {
            this.setOnClickListener{
                startActivity(
                    Intent(this@MainActivity, LoginActivity::class.java)
                )
            }
        }

        findViewById<Button>(R.id.signup_intent_button).apply {
            this.setOnClickListener{
                startActivity(
                    Intent(this@MainActivity, SignupActivity::class.java)
                )
            }
        }
    }
}