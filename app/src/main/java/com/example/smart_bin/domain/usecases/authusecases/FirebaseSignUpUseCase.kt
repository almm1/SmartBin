package com.example.smart_bin.domain.usecases.authusecases

import com.example.smart_bin.domain.repository.AuthRepository

class FirebaseSignUpUseCase(private val authRepository: AuthRepository) {
    fun execute(phoneNumber: String, fullName: String, image: String?) {
        authRepository.signUp(phoneNumber, fullName, image)
    }
}