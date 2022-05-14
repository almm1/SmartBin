package com.example.smart_bin.presentation.ui.login

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smart_bin.domain.usecases.authusecases.AuthUseCases
import com.example.smart_bin.presentation.base.BaseViewModel
import com.example.smart_bin.utils.Event
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*
import java.util.concurrent.TimeUnit

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

    private fun toRegistrationFragment(phoneNumber: String) {
        navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment(phoneNumber))
    }

    private fun sendCode(phoneNumber: String, activity: FragmentActivity) {
        authUseCases.firebaseSendCode.execute(phoneNumber, activity,
            onVerificationCompleted = {},
            onVerificationFailed = { message ->
                showToast(message)
            },
            onCodeSent = { id ->
                navigate(
                    LoginFragmentDirections.actionLoginFragmentToVerificationFragment(
                        phoneNumber,
                        id
                    )
                )
            })
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