package com.example.smart_bin.domain.repository

import androidx.fragment.app.FragmentActivity

interface AuthRepository {
    fun userIsRegistered(
        phoneNumber: String,
        isRegistered: () -> Unit,
        notRegistered: () -> Unit
    )

    fun signOut()
    fun sendCode(
        phoneNumber: String,
        activity: FragmentActivity,
        onVerificationCompleted: () -> Unit,
        onVerificationFailed: (String) -> Unit,
        onCodeSent: (String) -> Unit
    )
}