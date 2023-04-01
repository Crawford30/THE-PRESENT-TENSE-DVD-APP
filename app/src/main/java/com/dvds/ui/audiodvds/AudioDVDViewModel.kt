package com.dvds.ui.audiodvds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvds.data.network.Resource
import com.dvds.data.responses.audiodvd.AudioDVDResponse
import com.dvds.data.respository.AudioDVDRepository
import com.dvds.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class AudioDVDViewModel(
    private val repository: AudioDVDRepository
): BaseViewModel(repository) {
    private val _presentTenseAudioDVD: MutableLiveData<Resource<AudioDVDResponse>> = MutableLiveData()
    val presentTenseAudioDVD: LiveData<Resource<AudioDVDResponse>>
        get() = _presentTenseAudioDVD


    //Call the function created in the Audio DVD  Repository

    fun getPresentTenseAudioDVDLists() = viewModelScope.launch {
        _presentTenseAudioDVD.value = repository.getPresentTenseAudioDVD()
    }
}