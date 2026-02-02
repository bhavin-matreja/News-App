package com.bvn.newsapp.domain.usecases.news

import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.domain.repository.NewsRepository

class UpsertArticleUseCase(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: NewsArticle) {
        newsRepository.upsertArticle(article)
    }
}