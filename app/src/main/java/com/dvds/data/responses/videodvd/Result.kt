package com.dvds.data.responses.videodvd

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("creator_user_id")
    val creatorUserId: Int,
    val id: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("video_dvd_name")
    val videoDvdName: String,
    @SerializedName("video_dvd_path")
    val videoDvdPath: String,
    @SerializedName("video_dvd_permissions")
    val videoDvdPermissions: List<String>,
    @SerializedName("views_count")
    val viewsCount: Int

)