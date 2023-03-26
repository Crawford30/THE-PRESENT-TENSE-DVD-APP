package com.dvds.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvds.data.respository.BaseRepository
import com.dvds.data.respository.SongRepository
import com.dvds.datasource.AuthRepository
import com.dvds.ui.presenttense.PresentTenseViewModel
import com.dvds.ui.auth.AuthViewModel
import java.lang.IllegalArgumentException

//To give the view model
class ViewModelFactory(private  val  repository: BaseRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
        return  when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(PresentTenseViewModel::class.java) -> PresentTenseViewModel(repository as SongRepository) as T

            else -> throw IllegalArgumentException("View Model Class Not Found")
        }
    }
}