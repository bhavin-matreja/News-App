package com.bvn.newsapp.presentation.search

sealed interface SearchEvent {
    data class UpdateSearchQuery(val searchQuery: String): SearchEvent
    object SearchNews : SearchEvent
}