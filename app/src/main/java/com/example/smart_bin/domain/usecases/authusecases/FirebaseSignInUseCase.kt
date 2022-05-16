package com.example.smart_bin.domain.usecases.authusecases

import com.example.smart_bin.domain.repository.AuthRepository

class FirebaseSignInUseCase(private val authRepository: AuthRepository) {
    fun execute(
        uid: String,
        code: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        authRepository.signIn(uid, code, onSuccess, onFail)
    }
}
