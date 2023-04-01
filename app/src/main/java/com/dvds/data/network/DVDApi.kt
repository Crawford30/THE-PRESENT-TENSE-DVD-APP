package com.dvds.data.network

import com.dvds.data.responses.audiodvd.AudioDVDResponse
import com.dvds.data.responses.videodvd.VideoDVDResponse
import retrofit2.http.GET

interface DVDApi {

    //==video DVD
    @GET("/api/video-dvd/list")
    suspend fun getPresentTenseVideoDVD(): VideoDVDResponse

    //==Audio DVD
    @GET("/api/audio-dvd/list")
    suspend fun getPresentTenseAudioDVD(): AudioDVDResponse
}