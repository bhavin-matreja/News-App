package com.bvn.newsapp.domain.usecases.news

import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.domain.repository.NewsRepository

class GetArticleUseCase(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(url: String): NewsArticle? {
        return newsRepository.getArticle(url)
    }
}