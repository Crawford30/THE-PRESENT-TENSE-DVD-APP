package com.dvds.ui.base

import androidx.lifecycle.ViewModel
import com.dvds.data.network.AuthApi
import com.dvds.data.respository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract  class BaseViewModel(private val  repository: BaseRepository): ViewModel() {
    suspend fun logout(api: AuthApi) = withContext(Dispatchers.IO){repository.logout(api)}
}