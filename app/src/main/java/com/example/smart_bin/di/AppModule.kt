package com.example.smart_bin.di

import com.example.smart_bin.domain.usecases.authusecases.AuthUseCases
import com.example.smart_bin.presentation.ui.login.LoginViewModel
import com.example.smart_bin.presentation.ui.registration.RegistrationViewModel
import com.example.smart_bin.presentation.ui.verification.VerificationViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideLoginViewModelFactory(authUseCases: AuthUseCases): LoginViewModel.LoginViewModelFactory {
        return LoginViewModel.LoginViewModelFactory(authUseCases)
    }

    @Provides
    fun provideVerificationViewModelFactory(authUseCases: AuthUseCases): VerificationViewModel.VerificationViewModelFactory {
        return VerificationViewModel.VerificationViewModelFactory(authUseCases)
    }

    @Provides
    fun provideRegistrationViewModelFactory(authUseCases: AuthUseCases): RegistrationViewModel.RegistrationViewModelFactory {
        return RegistrationViewModel.RegistrationViewModelFactory(authUseCases)
    }
}