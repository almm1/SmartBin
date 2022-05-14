package com.example.smart_bin.di

import com.example.smart_bin.domain.repository.AuthRepository
import com.example.smart_bin.domain.usecases.authusecases.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideAuthUseCases(repository: AuthRepository) = AuthUseCases(
        firebaseSendCode = FirebaseSendCode(repository),
        firebaseSignInUseCase = FirebaseSignInUseCase(repository),
        firebaseSignOutUseCase = FirebaseSignOutUseCase(repository),
        firebaseSignUpUseCase = FirebaseSignUpUseCase(repository),
        firebaseUserIsRegistered = FirebaseUserIsRegistered(repository)
    )
}