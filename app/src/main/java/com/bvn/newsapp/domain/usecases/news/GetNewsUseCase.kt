package com.bvn.newsapp.domain.usecases.news

import androidx.paging.PagingData
import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(sources: List<String>): Flow<PagingData<NewsArticle>> {
        return repository.getTopHeadlines(sources = sources)
    }
}