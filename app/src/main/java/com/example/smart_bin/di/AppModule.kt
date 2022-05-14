package com.example.smart_bin.di

import com.example.smart_bin.domain.usecases.authusecases.AuthUseCases
import com.example.smart_bin.presentation.ui.login.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideLoginViewModelFactory(authUseCases: AuthUseCases): LoginViewModel.LoginViewModelFactory {
        return LoginViewModel.LoginViewModelFactory(authUseCases)

    }
}