package com.dvds.data.network

import com.dvds.data.responses.user.login.LoginResponse
import com.dvds.data.responses.user.register.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @FormUrlEncoded
    @POST("/api/auth/login")
    suspend fun login(
        @Field("email") email:String,
        @Field("password") password:String,
    ): LoginResponse

    @FormUrlEncoded
    @POST("/api/auth/register-user")
    suspend fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
    ): RegisterResponse


    @POST("/api/auth/logout")
    suspend fun logout(
    ): ResponseBody
}