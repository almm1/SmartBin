package com.example.smart_bin.data.repository

import android.net.Uri
import androidx.fragment.app.FragmentActivity
import com.example.smart_bin.domain.repository.AuthRepository
import com.example.smart_bin.utils.AppValueEventListener
import com.example.smart_bin.utils.Response
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

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
            .addOnCompleteListener {
//                if (it.isSuccessful) {
//
//                }
            }

        if (!image.isNullOrEmpty()) {
            val path = refStorage.child("userImage").child(uid)
            path.putFile(Uri.parse(image)).addOnCompleteListener {
                if(it.isSuccessful){

                }
            }
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
            } else {
                onFail(task.exception.toString())
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