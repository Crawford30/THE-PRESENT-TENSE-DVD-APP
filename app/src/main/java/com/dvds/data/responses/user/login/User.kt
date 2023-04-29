package com.dvds.data.responses.user.login


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("api_token")
    val apiToken: Any,
    @SerializedName("audio_dvd_permission")
    val audioDvdPermission: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("dvd_access_status")
    val dvdAccessStatus: String,
    val email: String,
    val id: Int,
    @SerializedName("is_email_verified")
    val isEmailVerified: String,
    val name: String,
    @SerializedName("song_access_status")
    val songAccessStatus: String,
    val type: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_status")
    val userStatus: String,
    @SerializedName("video_dvd_permission")
    val videoDvdPermission: Any
)