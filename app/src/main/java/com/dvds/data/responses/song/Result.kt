package com.dvds.data.responses.song


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("created_at")
    val createdAt: String,
    val id: Int,
    @SerializedName("song_body")
    val songBody: String,
    @SerializedName("song_number")
    val songNumber: String,
    @SerializedName("song_title")
    val songTitle: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)