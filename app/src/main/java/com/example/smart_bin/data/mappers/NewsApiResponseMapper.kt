package com.example.smart_bin.data.mappers

import com.example.smart_bin.data.api.NewsResponseApi
import com.example.smart_bin.presentation.model.News

class NewsApiResponseMapper {
    fun toEntityData(response: NewsResponseApi): List<News> {
        return response.results.map {
            News(
                it.title,
                it.imageUrl,
                it.pubDate,
                it.sourceId
            )
        }
    }
}