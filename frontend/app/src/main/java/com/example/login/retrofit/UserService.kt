package com.example.login.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

class  SignupResultData(
    val message: String
)
class LoginResultData(
    val message: String, val username: String
)

class UserSigninServer(
    val email: String, val password: String
)

class UserSignupServer(
    val email: String, val password: String, val username: String
)
interface UserService {
    @POST("api/user/signin")
    fun postSigninUser(
        @Body body: UserSigninServer
    ): Call<LoginResultData>

    @POST("api/user/signup")
    fun postSignupUser(
        @Body body: UserSignupServer
    ): Call<SignupResultData>
}