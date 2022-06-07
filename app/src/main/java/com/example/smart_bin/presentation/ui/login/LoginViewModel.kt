package com.example.smart_bin.presentation.ui.login

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smart_bin.domain.usecases.authusecases.AuthUseCases
import com.example.smart_bin.presentation.base.BaseViewModel
import com.example.smart_bin.presentation.model.RegUser

class LoginViewModel(private val authUseCases: AuthUseCases) : BaseViewModel() {

    fun onLoginButtonClick(phoneNumber: String, activity: FragmentActivity) {
        checkRegistrationUser(phoneNumber, activity)
    }

    private fun checkRegistrationUser(phoneNumber: String, activity: FragmentActivity) {
        authUseCases.firebaseUserIsRegistered.execute(
            phoneNumber,
            isRegistered = {
                sendCode(phoneNumber, activity)
            },
            notRegistered = {
                toRegistrationFragment(phoneNumber)
            })
    }

    private fun sendCode(phoneNumber: String, activity: FragmentActivity) {
        authUseCases.firebaseSendCode.execute(phoneNumber, activity,
            onVerificationFailed = { message ->
                showToast(message)
            },
            onCodeSent = { id ->
                toVerificationFragment(phoneNumber, id)
            })
    }

    private fun toRegistrationFragment(phoneNumber: String) {
        navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment(phoneNumber))
    }

    private fun toVerificationFragment(phoneNumber: String, id: String) {
        navigate(
            LoginFragmentDirections.actionLoginFragmentToVerificationFragment(
                RegUser(id = id, phone = phoneNumber)
            )
        )
    }

    class LoginViewModelFactory(
        private val authUseCases: AuthUseCases
    ) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(
                authUseCases
            ) as T
        }
    }
}