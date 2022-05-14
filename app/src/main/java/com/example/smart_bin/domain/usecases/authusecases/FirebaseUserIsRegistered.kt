package com.example.smart_bin.domain.usecases.authusecases

import com.example.smart_bin.domain.repository.AuthRepository

class FirebaseUserIsRegistered(private val authRepository: AuthRepository) {
    fun execute(
        phoneNumber: String,
        isRegistered: () -> Unit,
        notRegistered: () -> Unit
    ) {
        authRepository.userIsRegistered(phoneNumber, isRegistered, notRegistered)
    }
}