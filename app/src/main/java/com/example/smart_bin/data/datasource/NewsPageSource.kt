package com.example.smart_bin.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.smart_bin.presentation.model.News


class NewsPageSource(private val dataSource: NewsDataSource) :
    PagingSource<Int, News>() {

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val nextPageNumber = params.key ?: 0
            val news = dataSource.getNews(nextPageNumber)!!

            LoadResult.Page(
                data = news,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < news.size) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}