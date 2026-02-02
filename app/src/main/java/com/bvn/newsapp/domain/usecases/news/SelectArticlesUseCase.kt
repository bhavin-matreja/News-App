package com.bvn.newsapp.domain.usecases.news

import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticlesUseCase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<NewsArticle>> {
        return newsRepository.getAllArticles()
    }
}