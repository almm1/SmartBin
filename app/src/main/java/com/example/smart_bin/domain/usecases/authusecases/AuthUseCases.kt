package com.example.smart_bin.domain.usecases.authusecases

data class AuthUseCases(
    val firebaseSignOutUseCase: FirebaseSignOutUseCase,
    val firebaseSignUpUseCase: FirebaseSignUpUseCase,
    val firebaseUserIsRegistered: FirebaseUserIsRegistered,
    val firebaseSignInUseCase: FirebaseSignInUseCase,
    val firebaseSendCode: FirebaseSendCode
)