package com.example.smart_bin.di

import com.example.smart_bin.domain.usecases.GetNewsListUseCase
import com.example.smart_bin.domain.usecases.authusecases.AuthUseCases
import com.example.smart_bin.domain.usecases.userusecases.UserUseCases
import com.example.smart_bin.presentation.ui.bonuses.BonusesViewModel
import com.example.smart_bin.presentation.ui.home.HomeViewModel
import com.example.smart_bin.presentation.ui.news.NewsViewModel
import com.example.smart_bin.presentation.ui.login.LoginViewModel
import com.example.smart_bin.presentation.ui.registration.RegistrationViewModel
import com.example.smart_bin.presentation.ui.verification.VerificationViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideLoginViewModelFactory(authUseCases: AuthUseCases): LoginViewModel.LoginViewModelFactory {
        return LoginViewModel.LoginViewModelFactory(authUseCases)
    }

    @Singleton
    @Provides
    fun provideVerificationViewModelFactory(authUseCases: AuthUseCases): VerificationViewModel.VerificationViewModelFactory {
        return VerificationViewModel.VerificationViewModelFactory(authUseCases)
    }

    @Singleton
    @Provides
    fun provideRegistrationViewModelFactory(authUseCases: AuthUseCases): RegistrationViewModel.RegistrationViewModelFactory {
        return RegistrationViewModel.RegistrationViewModelFactory(authUseCases)
    }

    @Singleton
    @Provides
    fun provideHomeViewModelFactory(userUseCases: UserUseCases): HomeViewModel.HomeViewModelFactory {
        return HomeViewModel.HomeViewModelFactory(userUseCases)
    }

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(newsListUseCase: GetNewsListUseCase): NewsViewModel.NewsViewModelFactory {
        return NewsViewModel.NewsViewModelFactory(newsListUseCase)
    }

    @Singleton
    @Provides
    fun provideBonusViewModelFactory(userUseCases: UserUseCases): BonusesViewModel.BonusViewModelFactory {
        return BonusesViewModel.BonusViewModelFactory(userUseCases)
    }
}