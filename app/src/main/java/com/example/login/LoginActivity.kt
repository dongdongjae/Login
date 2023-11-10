package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.login.retrofit.LoginResultData
import com.example.login.retrofit.RetrofitConnection
import com.example.login.retrofit.UserService
import com.example.login.retrofit.UserSigninServer
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

                userLogin(email=userEmail, password=userPassword)
            }
        }
    }

    private fun userLogin(email: String, password: String) {
        if (email == "") {
            Toast.makeText(this@LoginActivity, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
        } else if (password == "") {
            Toast.makeText(this@LoginActivity, "비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show()
        } else {
            postUserSigninInfo(email, password)
        }
    }

    private fun postUserSigninInfo(email: String, password: String) {
        val retrofitAPI = RetrofitConnection.getInstance().create(UserService::class.java)
        val user = UserSigninServer(email, password)
        retrofitAPI.postSigninUser(user).enqueue(object : Callback<LoginResultData> {
            override fun onResponse(
                call: Call<LoginResultData>,
                response: Response<LoginResultData>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Toast.makeText(this@LoginActivity, result?.message, Toast.LENGTH_LONG).show()

                    val intent = Intent(Intent(this@LoginActivity, HomeActivity::class.java))
                    intent.putExtra("username", result?.username)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "다시 로그인해주세요.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResultData>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "다시 로그인해주세요.", Toast.LENGTH_LONG).show()
            }

        })
    }
}