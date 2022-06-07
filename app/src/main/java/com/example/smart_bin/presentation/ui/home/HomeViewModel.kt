package com.example.smart_bin.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smart_bin.domain.usecases.userusecases.UserUseCases
import com.example.smart_bin.presentation.base.BaseViewModel

class HomeViewModel(private val userUseCases: UserUseCases) : BaseViewModel() {

    fun initUser(onSuccess:()->Unit) {
        userUseCases.loadUserDataUseCase.execute(onSuccess)
    }


    class HomeViewModelFactory(
        private val userUseCases: UserUseCases
    ) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(
                userUseCases
            ) as T
        }
    }
}