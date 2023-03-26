package com.dvds.data.network

import com.dvds.data.responses.user.LoginResponse
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


    @POST("/api/auth/logout")
    suspend fun logout(
    ): ResponseBody
}