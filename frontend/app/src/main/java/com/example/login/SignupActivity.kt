package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.login.retrofit.RetrofitConnection
import com.example.login.retrofit.SignupResultData
import com.example.login.retrofit.UserApi
import com.example.login.retrofit.UserService
import com.example.login.retrofit.UserSignupServer
import com.example.userlibrary.UserValidate
import com.example.userlibrary.depends.MakeToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val emailTextView = findViewById<EditText>(R.id.signup_edit_email)
        val passwordOneTextView = findViewById<EditText>(R.id.signup_edit_password1)
        val passwordTwoTextView = findViewById<EditText>(R.id.signup_edit_password2)
        val usernameTextView = findViewById<EditText>(R.id.signup_edit_username)

        findViewById<TextView>(R.id.signup_button).apply {
            this.setOnClickListener {
                val userEmail = emailTextView.text.toString()
                val userPasswordOne = passwordOneTextView.text.toString()
                val userPasswordTwo = passwordTwoTextView.text.toString()
                val username = usernameTextView.text.toString()

                if (!UserValidate.validateSignupInfo(
                        userEmail,
                        userPasswordOne,
                        userPasswordTwo,
                        username
                    )
                ) {
                    MakeToast.shortToast(this@SignupActivity, "빠진 내용이 없는지 다시 한 번 확인해주세요.")
                } else {
                    if (!UserValidate.validatePassword(userPasswordOne, userPasswordTwo)) {
                        MakeToast.shortToast(this@SignupActivity, "비밀번호를 다시 확인해주세요.")
                    } else {
                        UserApi.postSignupInfo(
                            context = this@SignupActivity,
                            email = userEmail,
                            passwordOne = userPasswordOne,
                            username
                        )
                    }
                }
            }
        }
    }
}