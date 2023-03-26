package com.dvds.ui.presenttense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvds.data.network.Resource
import com.dvds.data.responses.song.SongResponse
import com.dvds.data.respository.SongRepository
import com.dvds.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class PresentTenseViewModel(
    private val repository: SongRepository
): BaseViewModel(repository) {
    private val _presentTenseSong: MutableLiveData<Resource<SongResponse>> = MutableLiveData()
    val presentTenseSong: LiveData<Resource<SongResponse>>
        get() = _presentTenseSong


    //Call the function created in the Song Repository

    fun getPresentTenseSongs() = viewModelScope.launch {
        _presentTenseSong.value = repository.getSongPresentTenseSong()
    }
}