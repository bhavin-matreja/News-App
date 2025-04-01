package com.bvn.newsapp.presentation.bookmark

import com.bvn.newsapp.domain.model.Article

data class BookmarkState(
    val articles: List<Article> = emptyList()
)
