package com.dvds.data.network

import com.dvds.data.responses.song.SongResponse
import retrofit2.http.GET


interface SongApi {

    //==Present Tense Song
    @GET("/api/present-tense/list")
    suspend fun getPresentTenseSong(): SongResponse
}