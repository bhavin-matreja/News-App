package com.bvn.newsapp.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bvn.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                state = state.copy(searchQuery = event.searchQuery)
            }

            SearchEvent.searchNews -> {
                searchNews()
            }
        }
    }

    private fun searchNews() {
        val articles = newsUseCases.searchNewsUseCase(
            searchQuery = state.searchQuery,
            sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
        ).cachedIn(viewModelScope)
        state = state.copy(articles = articles)
    }
}