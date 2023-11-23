package com.example.login.retrofit

import android.content.Context
import android.content.Intent
import android.util.Log

import com.example.login.HomeActivity
import com.example.login.LoginActivity
import com.example.userlibrary.UserValidate
import com.example.userlibrary.depends.MakeToast
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ErrorResponse(val detail: String)

object UserApi {
    val retrofitManager = RetrofitConnection()

    fun postSigninInfo(context: Context, email: String, password: String) {
        val userInfo = UserSigninServer(email, password)
        val userCall = retrofitManager.userService.postSigninUser(userInfo)

        userCall.enqueue(object : Callback<LoginResultData> {
            override fun onResponse(
                call: Call<LoginResultData>,
                response: Response<LoginResultData>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()

                    // TODO: save token

                    val intent = Intent(Intent(context, HomeActivity::class.java))
                    intent.putExtra("username", res?.username)
                    context.startActivity(intent)
                } else {
                    val errorMessage = response.errorBody()?.string()
                    val result = Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    MakeToast.shortToast(context, result.detail)
                }
            }

            override fun onFailure(call: Call<LoginResultData>, t: Throwable) {
                Log.e("error", t.message.toString())
                MakeToast.shortToast(context, "다시 시도해주세요.")
            }
        })
    }

    fun postSignupInfo(
        context: Context,
        email: String,
        passwordOne: String,
        username: String
    ) {
        val userInfo = UserSignupServer(email, passwordOne, username)
        val userCall = retrofitManager.userService.postSignupUser(userInfo)

        userCall.enqueue(object : Callback<SignupResultData> {
            override fun onResponse(
                call: Call<SignupResultData>,
                response: Response<SignupResultData>
            ) {
                if (response.isSuccessful) {
                    val msg = response.body()?.message.toString()
                    MakeToast.shortToast(context, msg)

                    val intent = Intent(Intent(context, LoginActivity::class.java))
                    context.startActivity(intent)
                } else {
                    val errorMessage = response.errorBody()?.string()
                    val result = Gson().fromJson(errorMessage, ErrorResponse::class.java)
                    MakeToast.shortToast(context, result.detail)
                }
            }

            override fun onFailure(call: Call<SignupResultData>, t: Throwable) {
                Log.e("error", t.message.toString())
                MakeToast.shortToast(context, "다시 시도해주세요.")
            }
        })


    }
}


