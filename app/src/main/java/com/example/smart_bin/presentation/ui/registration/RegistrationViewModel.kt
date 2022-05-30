package com.example.smart_bin.presentation.ui.registration

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.example.smart_bin.presentation.model.RegUser
import com.example.smart_bin.domain.usecases.authusecases.AuthUseCases
import com.example.smart_bin.presentation.base.BaseViewModel

class RegistrationViewModel(private val authUseCases: AuthUseCases) : BaseViewModel() {


    fun onRegistrationButtonClick(
        phoneNumber: String,
        name: String,
        surname: String,
        image: String,
        activity: FragmentActivity
    ) {

        registration(phoneNumber, name, surname, image, activity)
    }

    private fun registration(
        phoneNumber: String,
        name: String,
        surname: String,
        image: String,
        activity: FragmentActivity
    ) {
        authUseCases.firebaseSendCode.execute(phoneNumber, activity,
            onVerificationFailed = { message ->
                showToast(message)
            },
            onCodeSent = { id ->

                navigate(
                    RegistrationFragmentDirections.actionRegistrationFragmentToVerificationFragment(
                        RegUser(
                            id = id,
                            phone = phoneNumber,
                            full_name = "$name $surname",
                            image = image
                        )
                    )
                )
            })
    }


    class RegistrationViewModelFactory(
        private val authUseCases: AuthUseCases
    ) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegistrationViewModel(
                authUseCases
            ) as T
        }
    }
}