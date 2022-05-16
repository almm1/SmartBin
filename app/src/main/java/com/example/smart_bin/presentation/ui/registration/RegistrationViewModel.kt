package com.example.smart_bin.presentation.ui.registration

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smart_bin.domain.usecases.authusecases.AuthUseCases
import com.example.smart_bin.presentation.base.BaseViewModel

class RegistrationViewModel(private val authUseCases: AuthUseCases) : BaseViewModel() {

    fun onRegistrationButtonClick(
        phoneNumber: String,
        name: String,
        surname: String,
        activity: FragmentActivity
    ) {
        registration(phoneNumber, name, surname, activity)
    }

    private fun registration(
        phoneNumber: String,
        name: String,
        surname: String,
        activity: FragmentActivity
    ) {
        authUseCases.firebaseSendCode.execute(phoneNumber, activity,
            onVerificationFailed = { message ->
                showToast(message)
            },
            onCodeSent = { id ->
                val fullName = "$name $surname"
                navigate(
                    RegistrationFragmentDirections.actionRegistrationFragmentToVerificationFragment(
                        phoneNumber,
                        id,
                        fullName
                    )
                )
            })

//            val options = PhoneAuthOptions.newBuilder(mAuth)
//                .setPhoneNumber(phoneNumber)
//                .setActivity(requireActivity())
//                .setTimeout(60L, TimeUnit.SECONDS)
//                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
//
//                    override fun onVerificationFailed(exception: FirebaseException) {
//                        Toast.makeText(requireActivity(), exception.toString(), Toast.LENGTH_SHORT)
//                            .show()
//                    }
//
//                    override fun onCodeSent(
//                        id: String,
//                        token: PhoneAuthProvider.ForceResendingToken
//                    ) {
//                        super.onCodeSent(id, token)
//                        val fullName = "$name $surname"
//                        val userData = mutableMapOf<String, Any>()
//                        userData["phone"] = phoneNumber
//                        userData["full_name"] = fullName
//                        userData["id"] = id
//
//                        refDataBase.child("users").child(phoneNumber).updateChildren(userData)
//                            .addOnCompleteListener {
//                                if (it.isSuccessful) {
//                                    findNavController().navigate(
//                                        RegistrationFragmentDirections.actionRegistrationFragmentToVerificationFragment(
//                                            phoneNumber,
//                                            id
//                                        )
//                                    )
//                                } else {
//                                    Toast.makeText(
//                                        requireContext(),
//                                        it.exception.toString(),
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//                    }
//                })
//                .build()
//            PhoneAuthProvider.verifyPhoneNumber(options)

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