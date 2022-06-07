package com.example.smart_bin.presentation.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.smart_bin.domain.usecases.GetNewsListUseCase
import com.example.smart_bin.presentation.base.BaseViewModel

class NewsViewModel(private val getNewsListUseCase: GetNewsListUseCase) : BaseViewModel() {

    val news = getNewsListUseCase.execute().cachedIn(viewModelScope)

    class NewsViewModelFactory(
        private val getNewsListUseCase: GetNewsListUseCase
    ) :
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsViewModel(
                getNewsListUseCase
            ) as T
        }
    }
}