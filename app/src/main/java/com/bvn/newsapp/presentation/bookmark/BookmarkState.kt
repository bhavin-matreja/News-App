package com.bvn.newsapp.presentation.bookmark

import com.bvn.newsapp.domain.model.NewsArticle

data class BookmarkState(
    val articles: List<NewsArticle> = emptyList()
)
