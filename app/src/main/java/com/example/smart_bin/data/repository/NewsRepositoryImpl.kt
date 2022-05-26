package com.example.smart_bin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.smart_bin.data.datasource.NewsDataSource
import com.example.smart_bin.data.datasource.NewsPageSource
import com.example.smart_bin.domain.repository.NewsRepository

class NewsRepositoryImpl(private val dataSource: NewsDataSource) : NewsRepository {
    override fun get() = Pager(
        config = PagingConfig(
            PAGE_SIZE, initialLoadSize = INIT_LOAD_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            NewsPageSource(dataSource)
        }).flow

    companion object {
        private const val PAGE_SIZE = 10
        private const val INIT_LOAD_SIZE = PAGE_SIZE * 3
    }
}