package com.dvds.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.dvds.data.network.RemoteDataSource
import com.dvds.data.responses.user.UserPreferences
import com.dvds.data.respository.BaseRepository
import kotlinx.coroutines.launch

abstract class BaseFragment<VM:BaseViewModel,B: ViewBinding, R: BaseRepository>: Fragment() {
    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected lateinit var userPreferences: UserPreferences

    //instance of remote data source, to create api instance
    protected  val remoteDataSource = RemoteDataSource()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext()) //reworked when dependency injection is implemented
        binding = getFragmentBinding(inflater, container)

        //view model instance


        //we need a factory instance
        val factory = ViewModelFactory(getFragmentRepository())

        //we can create view model instance
        viewModel = ViewModelProvider(this,factory).get(getViewModel())

        //Call the function when the fragment get created
        lifecycleScope.launch {
            // userPreferences.authToken.first()
        }


        return binding.root
        //return super.onCreateView(inflater, container, savedInstanceState)
    }


    fun logout() = lifecycleScope.launch {
//        val authToken = userPreferences.authToken.first()
//        val api = remoteDataSource.buildApi(AuthApi::class.java,authToken)
//        //viewModel.logout(api)
//        userPreferences.clear() //clear local storage

        //Redirect to login activity
        //requireActivity().startNewActivity(AuthActivity::class.java)

    }
    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?):B

    abstract fun getFragmentRepository(): R




}