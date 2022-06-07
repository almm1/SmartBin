package com.example.smart_bin.presentation.ui.bonuses

import androidx.lifecycle.*
import com.example.smart_bin.domain.usecases.userusecases.UserUseCases
import com.example.smart_bin.presentation.base.BaseViewModel
import com.example.smart_bin.presentation.model.Bonus

class BonusesViewModel(private val userUseCases: UserUseCases) : BaseViewModel() {


    val bonus = MutableLiveData<List<Bonus>>()

    init {
        userUseCases.getUserBonusesUseCase.execute({}) {
            bonus.value = it
        }
    }

    class BonusViewModelFactory(
        private val userUseCases: UserUseCases
    ) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return BonusesViewModel(
                userUseCases
            ) as T
        }
    }
}