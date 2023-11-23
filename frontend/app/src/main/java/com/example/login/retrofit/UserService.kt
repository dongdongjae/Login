package com.example.login.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginResultData(val message: String, val username: String, val access_token: String)
data class UserSigninServer(val email: String, val password: String)

data class SignupResultData(val message: String)
data class UserSignupServer(val email: String, val password: String, val username: String)

interface UserService {
    @POST("api/user/signin")
    fun postSigninUser(@Body body: UserSigninServer): Call<LoginResultData>

    @POST("api/user/signup")
    fun postSignupUser(@Body body: UserSignupServer): Call<SignupResultData>
}