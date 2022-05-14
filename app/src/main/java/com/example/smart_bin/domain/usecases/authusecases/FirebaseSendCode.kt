package com.example.smart_bin.domain.usecases.authusecases

import androidx.fragment.app.FragmentActivity
import com.example.smart_bin.domain.repository.AuthRepository

class FirebaseSendCode(private val authRepository: AuthRepository) {
    fun execute(
        phoneNumber: String,
        activity: FragmentActivity,
        onVerificationCompleted: () -> Unit,
        onVerificationFailed: (String) -> Unit,
        onCodeSent: (String) -> Unit
    ) {
        authRepository.sendCode(
            phoneNumber,
            activity,
            onVerificationCompleted,
            onVerificationFailed,
            onCodeSent
        )
    }
}