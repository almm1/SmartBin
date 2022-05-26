package com.example.smart_bin.di

import com.example.smart_bin.presentation.ui.news.NewsFragment
import com.example.smart_bin.presentation.ui.login.LoginFragment
import com.example.smart_bin.presentation.ui.registration.RegistrationFragment
import com.example.smart_bin.presentation.ui.verification.VerificationFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun injectLogin(loginFragment: LoginFragment)
    fun injectVerification(verificationFragment: VerificationFragment)
    fun injectRegistration(registrationFragment: RegistrationFragment)
    fun injectNews(newsFragment: NewsFragment)
}