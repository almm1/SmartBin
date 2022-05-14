package com.example.smart_bin.data.repository

import androidx.fragment.app.FragmentActivity
import com.example.smart_bin.domain.repository.AuthRepository
import com.example.smart_bin.utils.AppValueEventListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(
    private val mAuth: FirebaseAuth,
    private val database: FirebaseDatabase
) : AuthRepository {


    override fun signOut() {
        mAuth.signOut()
    }

    override fun userIsRegistered(
        phoneNumber: String,
        isRegistered: () -> Unit,
        notRegistered: () -> Unit
    ) {
        val refDataBase = database.reference
        refDataBase.child("users")
            .addListenerForSingleValueEvent(AppValueEventListener { snapshot ->
                if (snapshot.hasChild(phoneNumber)) {
                    isRegistered()
                } else {
                    notRegistered()
                }
            })
    }

    override fun sendCode(
        phoneNumber: String,
        activity: FragmentActivity,
        onVerificationCompleted: () -> Unit,
        onVerificationFailed: (String) -> Unit,
        onCodeSent: (String) -> Unit
    ) {
        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                onVerificationCompleted()
//                    mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
//                        }
//                    }
            }

            override fun onVerificationFailed(exception: FirebaseException) {
                onVerificationFailed(exception.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                onCodeSent(id)
            }
        }

        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)
            .setActivity(activity)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callback)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}