package com.example.smart_bin.presentation.ui.verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smart_bin.domain.usecases.authusecases.AuthUseCases
import com.example.smart_bin.presentation.base.BaseViewModel

class VerificationViewModel(private val authUseCases: AuthUseCases) : BaseViewModel() {

    fun onVerifyButtonClick(
        code: String,
        uid: String,
        phoneNumber: String,
        fullName: String?
    ) {
        verification(code, uid, phoneNumber, fullName)
    }

    private fun verification(code: String, uid: String, phoneNumber: String, fullName: String?) {
        authUseCases.firebaseSignInUseCase.execute(
            uid,
            code,
            onSuccess = {
                signUp(fullName, phoneNumber)
                toHomeFragment()
            },
            onFail = { showToast(it) }
        )
    }

    private fun toHomeFragment() {
        navigate(VerificationFragmentDirections.actionVerificationFragmentToHomeFragment())
    }

    private fun signUp(fullName: String?, phoneNumber: String) {
        if (fullName != null) {
            authUseCases.firebaseSignUpUseCase.execute(phoneNumber, fullName)
        }
    }

    class VerificationViewModelFactory(
        private val authUseCases: AuthUseCases
    ) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VerificationViewModel(
                authUseCases
            ) as T
        }
    }
}