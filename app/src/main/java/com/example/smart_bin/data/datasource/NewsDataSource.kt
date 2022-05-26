package com.example.smart_bin.data.datasource

import android.util.Log
import com.example.smart_bin.BuildConfig
import com.example.smart_bin.data.api.NewsApi
import com.example.smart_bin.data.mappers.NewsApiResponseMapper
import com.example.smart_bin.presentation.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsDataSource (private val service: NewsApi, private val mapper: NewsApiResponseMapper) {

    suspend fun getNews(page:Int): List<News>? =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getNews(page, BuildConfig.NEWS_API_KEY).execute()
                if (response.isSuccessful) {
                    return@withContext mapper.toEntityData(response.body()!!)
                } else {
                    return@withContext null
                }
            } catch (e: Exception) {
                Log.e("sourceError", e.toString())
                return@withContext null
            }
        }
}