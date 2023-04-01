package com.dvds.ui.videodvds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvds.data.network.Resource
import com.dvds.data.responses.videodvd.VideoDVDResponse
import com.dvds.data.respository.VideoDVDRepository
import com.dvds.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class VideoDVDViewModel(
    private val repository: VideoDVDRepository
): BaseViewModel(repository) {
    private val _presentTenseVideoDVD: MutableLiveData<Resource<VideoDVDResponse>> = MutableLiveData()
    val presentTenseVideoDVD: LiveData<Resource<VideoDVDResponse>>
        get() = _presentTenseVideoDVD


    //Call the function created in the Video DVD  Repository

    fun getPresentTenseVideoDVDLists() = viewModelScope.launch {
        _presentTenseVideoDVD.value = repository.getPresentTenseVideoDVD()
    }
}