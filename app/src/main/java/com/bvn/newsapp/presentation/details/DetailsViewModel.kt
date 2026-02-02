package com.bvn.newsapp.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private var _sideEffect = Channel<String>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var job: Job? = null

    fun onEvent(event: DetailsEvent) {
        when (event) {
            DetailsEvent.OnBackClicked -> TODO()
            DetailsEvent.onBrowsingClicked -> TODO()
            is DetailsEvent.UpsertDeleteArticle -> {
                job?.cancel()
                job = viewModelScope.launch {
                    val article = newsUseCases.getArticleUseCase(event.article.url)
                    if (article == null) {
                        upsertArticle(event.article)
                    } else {
                        deleteArticle(event.article)
                    }
                }
            }
        }
    }

    private suspend fun deleteArticle(article: NewsArticle) {
        newsUseCases.deleteArticleUseCase(article)
        _sideEffect.send("Article Removed")
    }

    private suspend fun upsertArticle(article: NewsArticle) {
        newsUseCases.upsertArticleUseCase(article)
        _sideEffect.send("Article Saved")
    }
}