package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.login.retrofit.RetrofitConnection
import com.example.login.retrofit.SignupResultData
import com.example.login.retrofit.UserService
import com.example.login.retrofit.UserSignupServer
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

                userSignup(userEmail, userPasswordOne, userPasswordTwo, username)
            }
        }
    }
    private fun userSignup(email: String, password1: String, password2: String, username: String) {
        if (email == "") {
            Toast.makeText(this@SignupActivity, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
        } else if (username == "") {
            Toast.makeText(this@SignupActivity, "닉네임을 입력해주세요", Toast.LENGTH_LONG).show()
        } else if (password1 == "") {
            Toast.makeText(this@SignupActivity, "비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show()
        } else if (password2 == "") {
            Toast.makeText(this@SignupActivity, "비밀번호를 한번 더 입력해주세요", Toast.LENGTH_LONG).show()
        } else if (password1 != password2) {
            Toast.makeText(this@SignupActivity, "비밀번호가 서로 다릅니다. 다시 확인해주세요", Toast.LENGTH_LONG)
                .show()
        } else {
            postUserSignupInfo(email, password1, username)
        }
    }

    private fun postUserSignupInfo(email: String, password: String, username: String) {
        val retrofitApi = RetrofitConnection.getInstance().create(UserService::class.java)
        val user = UserSignupServer(email, password, username)
        retrofitApi.postSignupUser(user).enqueue(object : Callback<SignupResultData> {
            override fun onResponse(
                call: Call<SignupResultData>,
                response: Response<SignupResultData>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Toast.makeText(this@SignupActivity, result?.message, Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                } else {
                    Toast.makeText(
                        this@SignupActivity,
                        "회원가입에 실패했습니다. 다시 시도해주세요.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<SignupResultData>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
}