package com.example.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val textView = findViewById<TextView>(R.id.home_textview)
        val intent = intent
        val data:String? = intent.extras?.getString("username")

        if(data != null){
            textView.text = "$data 님, 안녕하세요."
        }
    }
}