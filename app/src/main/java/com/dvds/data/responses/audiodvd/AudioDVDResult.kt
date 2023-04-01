package com.dvds.data.responses.audiodvd


import com.google.gson.annotations.SerializedName

data class AudioDVDResult(
    @SerializedName("audio_dvd_name")
    val audioDvdName: String,
    @SerializedName("audio_dvd_path")
    val audioDvdPath: String,
    @SerializedName("audio_dvd_permissions")
    val audioDvdPermissions: List<String>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("creator_user_id")
    val creatorUserId: Int,
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("views_count")
    val viewsCount: Int
)