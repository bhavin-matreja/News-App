package com.bvn.newsapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.bvn.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    var state = _state.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val articles = _state
        .map { it.searchQuery }
        .distinctUntilChanged()
        .debounce(400)
        .filter { it.isNotBlank() }
        .flatMapLatest { query ->
            newsUseCases.searchNewsUseCase(
                searchQuery = query,
                sources = listOf("bbc-news", "abc-news")
            ).cachedIn(viewModelScope)
        }

    fun onEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.SearchNews -> {
                // not required now
                //searchNews()
            }
            is SearchEvent.UpdateSearchQuery -> {
                _state.update {
                    it.copy(searchQuery = event.searchQuery)
                }
            }
        }
    }
}