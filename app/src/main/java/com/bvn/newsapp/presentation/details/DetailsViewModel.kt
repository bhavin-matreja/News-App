package com.bvn.newsapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bvn.newsapp.domain.model.Article
import com.bvn.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)

    fun onEvent(event: DetailsEvent) {
        when (event) {
            DetailsEvent.onBackClicked -> {}
            DetailsEvent.onBookmarkClicked -> {}
            DetailsEvent.onBrowsingClick -> {}
            DetailsEvent.removeSideEffect -> {
                sideEffect = null
            }
            is DetailsEvent.upsertDeleteArticle -> {
                viewModelScope.launch {
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

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticleUseCase(article)
        sideEffect = "Article Deleted"
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticleUseCase(article)
        sideEffect = "Article Saved"
    }
}