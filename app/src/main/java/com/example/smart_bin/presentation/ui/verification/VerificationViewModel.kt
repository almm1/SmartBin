package com.example.smart_bin.presentation.ui.verification

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smart_bin.domain.usecases.authusecases.AuthUseCases
import com.example.smart_bin.presentation.base.BaseViewModel
import com.example.smart_bin.presentation.model.RegUser

class VerificationViewModel(private val authUseCases: AuthUseCases) : BaseViewModel() {

    fun onVerifyButtonClick(
        code: String,
        regUser: RegUser
    ) {
        verification(code, regUser)
    }

    private fun verification(code: String, regUser: RegUser) {
        authUseCases.firebaseSignInUseCase.execute(
            regUser.id,
            code,
            onSuccess = {
                signUp(regUser.full_name, regUser.phone, regUser.image)
                toHomeFragment()
            },
            onFail = { showToast(it) }
        )
    }

    private fun toHomeFragment() {
        navigate(VerificationFragmentDirections.actionVerificationFragmentToHomeFragment())
    }

    private fun signUp(fullName: String?, phoneNumber: String, image:String?) {
        if (!fullName.isNullOrEmpty()) {
            authUseCases.firebaseSignUpUseCase.execute(phoneNumber, fullName, image)
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