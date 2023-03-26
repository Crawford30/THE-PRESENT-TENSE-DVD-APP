package com.dvds.data.respository

import com.dvds.data.network.SongApi

class SongRepository(private val api: SongApi) : BaseRepository() {

    suspend fun getSongPresentTenseSong() = safeApiCall {
        api.getPresentTenseSong()
    }

}