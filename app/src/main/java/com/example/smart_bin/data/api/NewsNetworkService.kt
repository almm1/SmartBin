package com.example.smart_bin.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NewsNetworkService {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsdata.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}
