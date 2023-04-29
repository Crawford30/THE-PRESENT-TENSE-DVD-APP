package com.dvds.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.dvds.MainActivity
import com.dvds.R
import com.dvds.data.network.AuthApi
import com.dvds.data.network.Resource
import com.dvds.databinding.FragmentLoginBinding
import com.dvds.datasource.AuthRepository
import com.dvds.helpers.enable
import com.dvds.helpers.handleApiError
import com.dvds.helpers.startNewActivity
import com.dvds.helpers.visible
import com.dvds.ui.base.BaseFragment
import com.dvds.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.progressbar.visible(false)
        binding.btnLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(true)
            when (it) {
                is Resource.Success -> {
                   // Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                    lifecycleScope.launch {

                        viewModel.saveAuthToken(it.value.token.toString())
                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it){login()} //passing login function to retry error
            }
        })

        binding.progressbar.visible(false)
        binding.btnLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(true)
            when (it) {
                is Resource.Success -> {
                   // Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                    lifecycleScope.launch {

                        viewModel.saveAuthToken(it.value.token.toString())
                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it){login()} //passing login function to retry error
            }
        })


        binding.inputPasswordLogin.addTextChangedListener {
            val email = binding.inputEmailLogin.text.toString().trim()
            binding.btnLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.btnLogin.setOnClickListener {
            login()
        }
//        binding.showPassBtn.setOnClickListener {
//            Toast.makeText(activity, "PASS WORD BUTTON TAPPED",
//                Toast.LENGTH_LONG).show()
//            ShowHidePass(this)
//        }


        binding.backToRegister.setOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_to_register) }
        }
    }





    private fun login(){
        val email = binding.inputEmailLogin.text.toString().trim()
        val password = binding.inputPasswordLogin.text.toString().toString()
        //hit the api using view model
        //binding.progressbar.visible(true)
        viewModel.login(email, password)
    }


    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentLoginBinding.inflate(inflater,container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)


//    fun ShowHidePass(view:View) {
//        if (view.id === R.id.show_pass_btn)
//        {
//            if (binding.inputPasswordLogin.transformationMethod.equals(PasswordTransformationMethod.getInstance()))
//            {
//                ((view) as ImageView).setImageResource(R.drawable.ic_visibility_off_black)
//                //Show Password
//                binding.inputPasswordLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
//            }
//            else
//            {
//                ((view) as ImageView).setImageResource(R.drawable.ic_visibility_black)
//                //Hide Password
//                binding.inputPasswordLogin.transformationMethod = PasswordTransformationMethod.getInstance()
//            }
//
//            // cursor reset his position so we need set position to the end of text
//            binding.inputPasswordLogin.setSelection(binding.inputPasswordLogin.text.length)
//        }
//    }


//
//    //========HIDE AND UNHIDE PASSWORD=======
//    fun ShowHidePass(view: LoginFragment) {
//        if (view.getId() === R.id.show_pass_btn)
//        {
//            if (inputPasswordLogin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance()))
//            {
//                ((view) as ImageView).setImageResource(R.drawable.ic_visibility_off_black)
//                //Show Password
//                inputPasswordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
//            }
//            else
//            {
//                ((view) as ImageView).setImageResource(R.drawable.ic_visibility_black)
//                //Hide Password
//                inputPasswordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance())
//            }
//
//            // cursor reset his position so we need set position to the end of text
//            inputPasswordLogin.setSelection(inputPasswordLogin.getText().length)
//        }
//    }


}
