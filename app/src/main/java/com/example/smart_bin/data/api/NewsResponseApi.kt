package com.example.smart_bin.data.api

import com.google.gson.annotations.SerializedName

data class NewsResponseApi(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("results") var results: ArrayList<Results> = arrayListOf(),
    @SerializedName("nextPage") var nextPage: Int? = null
)

data class Results(
    @SerializedName("title") var title: String,
    @SerializedName("link") var link: String? = null,
    @SerializedName("keywords") var keywords: ArrayList<String> = arrayListOf(),
    @SerializedName("creator") var creator: ArrayList<String> = arrayListOf(),
    @SerializedName("video_url") var videoUrl: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("pubDate") var pubDate: String,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("source_id") var sourceId: String? = null,
    @SerializedName("country") var country: ArrayList<String> = arrayListOf(),
    @SerializedName("category") var category: ArrayList<String> = arrayListOf(),
    @SerializedName("language") var language: String? = null
)