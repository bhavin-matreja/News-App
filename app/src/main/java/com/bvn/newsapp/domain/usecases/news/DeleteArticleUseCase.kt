package com.bvn.newsapp.domain.usecases.news

import com.bvn.newsapp.data.local.NewsDao
import com.bvn.newsapp.domain.model.Article
import com.bvn.newsapp.domain.repository.NewsRepository

class DeleteArticleUseCase(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(article)
    }
}