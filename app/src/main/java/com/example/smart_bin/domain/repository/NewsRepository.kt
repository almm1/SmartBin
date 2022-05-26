package com.example.smart_bin.domain.repository

import androidx.paging.PagingData
import com.example.smart_bin.presentation.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
     fun get(): Flow<PagingData<News>>
}