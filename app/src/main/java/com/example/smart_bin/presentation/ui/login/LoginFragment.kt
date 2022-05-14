package com.example.smart_bin.presentation.ui.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.smart_bin.databinding.LoginFragmentBinding
import com.example.smart_bin.presentation.base.BaseFragment

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LoginFragmentBinding =
        LoginFragmentBinding::inflate

    override val viewModel: LoginViewModel by viewModels()

    override fun setup() {
        binding.loginButton.setOnClickListener {
            val phoneNumber = binding.phoneNumberTextField.text.toString()
            viewModel.onLoginButtonClick(phoneNumber, requireActivity())
        }
    }
}