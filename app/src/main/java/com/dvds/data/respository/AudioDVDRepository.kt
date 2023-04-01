package com.dvds.data.respository

import com.dvds.data.network.DVDApi

class AudioDVDRepository(private val api: DVDApi) : BaseRepository() {
    suspend fun getPresentTenseAudioDVD() = safeApiCall {
        api.getPresentTenseAudioDVD()
    }

}