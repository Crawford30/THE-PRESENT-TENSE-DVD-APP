package com.dvds.data.respository

import com.dvds.data.network.DVDApi


class VideoDVDRepository(private val api: DVDApi) : BaseRepository() {

    suspend fun getPresentTenseVideoDVD() = safeApiCall {
        api.getPresentTenseVideoDVD()
    }

}