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

                val validateResult = UserValidate.validateSignupInfo(userEmail, userPasswordOne, userPasswordTwo, username)
                if(validateResult){
                    postUserSignupInfo(userEmail, userPasswordOne, username)
                }else{
                    if(!UserValidate.validatePassword(userPasswordOne, userPasswordTwo)){
                        MakeToast.shortToast(this@SignupActivity, "비밀번호가 일치하지 않습니다.")
                    }else{
                        MakeToast.shortToast(this@SignupActivity, "빠진 내용이 없는지 다시 한 번 확인해주세요.")
                    }
                }
            }
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