package com.example.smart_bin.presentation.ui.login

import androidx.fragment.app.FragmentActivity
import com.example.smart_bin.presentation.base.BaseViewModel
import com.example.smart_bin.utils.Event
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*
import java.util.concurrent.TimeUnit

class LoginViewModel: BaseViewModel(){

    fun onLoginButtonClick(phoneNumber: String, activity: FragmentActivity) {
        statusMessage.value = Event(phoneNumber)
        sendCode(phoneNumber, activity)
    }

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var refDataBase: DatabaseReference = FirebaseDatabase.getInstance().reference

    private fun sendCode(phoneNumber: String, activity: FragmentActivity) {
        if (phoneNumber.isBlank()) {
            statusMessage.value = Event("Поле пустое")
        } else {

            refDataBase.child("users").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(phoneNumber)) {
                        authUser(phoneNumber, activity)
                    } else {
                        registerUser(phoneNumber)
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }
    private fun registerUser(phoneNumber: String) {
        navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment(phoneNumber))
    }

    private fun authUser(phoneNumber: String, activity: FragmentActivity) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)
            .setActivity(activity)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                        }
                    }
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    statusMessage.value = Event(exception.toString())
                }

                override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(id, token)
                    navigate(
                        LoginFragmentDirections.actionLoginFragmentToVerificationFragment(
                            phoneNumber,
                            id
                        )
                    )
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}