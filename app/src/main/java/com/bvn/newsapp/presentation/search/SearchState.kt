package com.bvn.newsapp.presentation.search

import androidx.paging.PagingData
import com.bvn.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Article>>? = null
)