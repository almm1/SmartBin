package com.example.smart_bin.presentation.ui.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.smart_bin.App
import com.example.smart_bin.R
import com.example.smart_bin.databinding.LoginFragmentBinding
import com.example.smart_bin.presentation.base.BaseFragment
import javax.inject.Inject

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LoginFragmentBinding =
        LoginFragmentBinding::inflate

    override lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var vmFactory: LoginViewModel.LoginViewModelFactory

    override fun setup() {
        (requireActivity().application as App).appComponent.injectLogin(this)
        viewModel = ViewModelProvider(this, vmFactory)[LoginViewModel::class.java]

        binding.loginButton.setOnClickListener {
            val phoneNumber = binding.phoneNumberTextField.text.toString()
            if (phoneNumber.isBlank()) {
                showToast(getString(R.string.empty_field))
            } else {
                viewModel.onLoginButtonClick(phoneNumber, requireActivity())
            }
        }
    }
}