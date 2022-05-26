package com.example.smart_bin.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("api/1/news?")
    fun getNews(
        @Query("page") page: Int,
        @Query("apikey") apiKey: String,
        @Query("country") country: String = "ru"
    ): Call<NewsResponseApi>
}
