package com.dvds.data.responses.user.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("expires_at")
    val expiresAt: String,
    val message: String,
    val token: String,
    @SerializedName("token_type")
    val tokenType: String,
    val user: User
)