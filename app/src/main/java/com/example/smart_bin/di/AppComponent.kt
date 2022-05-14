package com.example.smart_bin.di

import com.example.smart_bin.presentation.ui.login.LoginFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(loginFragment: LoginFragment)
}