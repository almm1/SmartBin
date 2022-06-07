package com.example.smart_bin.data.repository

import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.example.smart_bin.domain.repository.AuthRepository
import com.example.smart_bin.utils.AppValueEventListener
import com.example.smart_bin.utils.Response
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(
    private val mAuth: FirebaseAuth,
    private val database: FirebaseDatabase,
    private val storage: FirebaseStorage
) : AuthRepository {


//    override fun signOut() {
//        mAuth.signOut()
//    }

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

    override fun signOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            mAuth.signOut()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An Unexpected Error"))
        }
    }

    override fun signIn(
        uid: String,
        code: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit,
    ) {
        val credential = PhoneAuthProvider.getCredential(uid, code)
        signInWithCredential(credential, onSuccess, onFail)
    }

    override fun signUp(phoneNumber: String, fullName: String, image: String?) {
        val refDataBase = database.reference
        val refStorage = storage.reference

        val uid = mAuth.currentUser?.uid.toString()
        val userData = mutableMapOf<String, Any>()
        userData["phone"] = phoneNumber
        userData["full_name"] = fullName
        userData["id"] = uid

        refDataBase.child("users").child(phoneNumber).updateChildren(userData)

        if (!image.isNullOrEmpty()) {
            val path = refStorage.child("userImage").child(uid)
            path.putFile(Uri.parse(image))
        }
    }

    private fun signInWithCredential(
        credential: PhoneAuthCredential,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
                loadToken()
            } else {
                onFail(task.exception.toString())
            }
        }
    }

    private fun loadToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                val dataToken = mutableMapOf<String, Any>()
                dataToken[token] = mAuth.currentUser?.uid.toString()
                database.reference.child("tokens").updateChildren(dataToken)
            }
        }
    }


    override fun sendCode(
        phoneNumber: String,
        activity: FragmentActivity,
        onVerificationFailed: (String) -> Unit,
        onCodeSent: (String) -> Unit
    ) {
        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithCredential(credential, {}, {})
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