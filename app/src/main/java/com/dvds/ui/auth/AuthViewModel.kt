package com.dvds.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvds.data.network.Resource
import com.dvds.data.responses.user.LoginResponse
import com.dvds.datasource.AuthRepository
import com.dvds.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(private  val repository: AuthRepository): BaseViewModel(repository) {
    //the view model communicates with the repository
    //By the help of repository we call the function

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(email, password)
    }

    suspend fun saveAuthToken(token: String) = repository.saveAuthToken(token)



//    suspend fun saveAuthToken(token: String) {
//        repository.saveAuthToken(token)
//    }
//    suspend fun saveAccessTokens(accessToken: String, refreshToken: String) {
//        repository.saveAccessTokens(accessToken, refreshToken)
//    }
}