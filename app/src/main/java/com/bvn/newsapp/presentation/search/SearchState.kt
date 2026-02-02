package com.bvn.newsapp.presentation.search

data class SearchState(
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val error: String? = null
)