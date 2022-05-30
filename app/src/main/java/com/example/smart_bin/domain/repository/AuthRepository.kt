package com.example.smart_bin.domain.repository

import androidx.fragment.app.FragmentActivity
import com.example.smart_bin.utils.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun userIsRegistered(
        phoneNumber: String,
        isRegistered: () -> Unit,
        notRegistered: () -> Unit
    )

    fun signOut(): Flow<Response<Boolean>>

    fun sendCode(
        phoneNumber: String,
        activity: FragmentActivity,
        onVerificationFailed: (String) -> Unit,
        onCodeSent: (String) -> Unit
    )

    fun signIn(
        uid: String,
        code: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    )

    fun signUp(phoneNumber: String, fullName: String, image: String?)
}