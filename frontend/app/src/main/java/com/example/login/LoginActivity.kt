package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.login.retrofit.LoginResultData
import com.example.login.retrofit.RetrofitConnection
import com.example.login.retrofit.UserApi
import com.example.login.retrofit.UserService
import com.example.login.retrofit.UserSigninServer
import com.example.userlibrary.UserValidate
import com.example.userlibrary.depends.MakeToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailTextView = findViewById<EditText>(R.id.signin_edit_email)
        val passwordTextView = findViewById<EditText>(R.id.signin_edit_password)


        findViewById<TextView>(R.id.signin_button).apply {
            this.setOnClickListener {
                val userEmail = emailTextView.text.toString()
                val userPassword = passwordTextView.text.toString()

                val validateResult =
                    UserValidate.isPossibleSignin(email = userEmail, password = userPassword)

                if(!validateResult.result){
                    MakeToast.shortToast(this@LoginActivity, validateResult.message)
                }else{
                    UserApi.postSigninInfo(this@LoginActivity, userEmail, userPassword)
                }
            }
        }
    }
}