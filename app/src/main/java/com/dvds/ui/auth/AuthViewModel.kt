package com.dvds.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvds.data.network.Resource
import com.dvds.data.responses.user.login.LoginResponse
import com.dvds.data.responses.user.register.RegisterResponse
import com.dvds.datasource.AuthRepository
import com.dvds.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(private  val repository: AuthRepository): BaseViewModel(repository) {
    //the view model communicates with the repository
    //By the help of repository we call the function

    //===Login==
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    //========Login In Fun
    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }




    //=====Register
    private val _registerResponse: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val registerResponse: LiveData<Resource<RegisterResponse>>
        get() = _registerResponse

    //=====Register Fun
    fun register(
        name: String,
        email: String,
        password: String

    ) = viewModelScope.launch {
        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.register(name,email, password)
    }


    suspend fun saveAuthToken(token: String) = repository.saveAuthToken(token)



//    suspend fun saveAuthToken(token: String) {
//        repository.saveAuthToken(token)
//    }
//    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
//        repository.saveAccessTokens(accessToken, refreshToken)
//    }
}