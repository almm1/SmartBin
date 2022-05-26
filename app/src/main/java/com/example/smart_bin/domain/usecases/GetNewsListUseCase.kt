package com.example.smart_bin.domain.usecases

import androidx.paging.PagingData
import com.example.smart_bin.domain.repository.NewsRepository
import com.example.smart_bin.presentation.model.News
import kotlinx.coroutines.flow.Flow

class GetNewsListUseCase(private val newsRepository: NewsRepository) {
     fun execute(): Flow<PagingData<News>> {
        return newsRepository.get()
    }
}