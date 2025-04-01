package com.bvn.newsapp.domain.usecases.news

import com.bvn.newsapp.data.local.NewsDao
import com.bvn.newsapp.domain.model.Article
import com.bvn.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticlesUseCase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsRepository.getAllArticles()
    }
}