package com.dvds.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.dvds.MainActivity
import com.dvds.R
import com.dvds.data.network.AuthApi
import com.dvds.data.network.Resource
import com.dvds.databinding.FragmentLoginBinding
import com.dvds.databinding.FragmentRegisterBinding
import com.dvds.datasource.AuthRepository
import com.dvds.helpers.enable
import com.dvds.helpers.handleApiError
import com.dvds.helpers.startNewActivity
import com.dvds.helpers.visible
import com.dvds.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        binding.progressbar.visible(false)
        binding.btnRegister.enable(false)

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
                is Resource.Failure -> handleApiError(it){register()} //passing register function to retry error
            }
        })

        binding.progressbar.visible(false)
        binding.btnRegister.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(true)
            when (it) {
                is Resource.Success -> {
                    // Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                    lifecycleScope.launch {

                        //Reset tje states
                        inputEmail.text.clear()
                        password.text.clear()
                        inputConfirmPassword.text.clear()


                        //view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_to_login) }
//                        viewModel.saveAuthToken(it.value.token.toString())
//                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it){register()} //passing register function to retry error
            }
        })


        binding.inputConfirmPassword.addTextChangedListener {
            val email = binding.inputEmail.text.toString().trim()
            binding.btnRegister.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.backToLogin.setOnClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_to_login) }
        }
//        binding.showPassBtn.setOnClickListener {
//            Toast.makeText(activity, "PASS WORD BUTTON TAPPED",
//                Toast.LENGTH_LONG).show()
//            ShowHidePass(this)
//        }
    }




    private fun register(){
        val name = "Joel Craw"
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputConfirmPassword.text.toString().toString()
        //hit the api using view model
        //binding.progressbar.visible(true)
        viewModel.register(name,email, password)
    }


    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentRegisterBinding.inflate(inflater,container, false)

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


