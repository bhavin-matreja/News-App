package com.bvn.newsapp.data.remote.dto


import com.bvn.newsapp.domain.model.Article
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)