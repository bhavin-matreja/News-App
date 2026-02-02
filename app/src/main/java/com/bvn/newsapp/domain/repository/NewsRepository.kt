package com.bvn.newsapp.domain.repository

import androidx.paging.PagingData
import com.bvn.newsapp.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(sources: List<String>): Flow<PagingData<NewsArticle>>
    fun searchNews(searchQuery:String, sources: List<String>): Flow<PagingData<NewsArticle>>
    suspend fun getArticle(url:String): NewsArticle?
    fun getAllArticles():Flow<List<NewsArticle>>
    suspend fun upsertArticle(article: NewsArticle)
    suspend fun deleteArticle(article: NewsArticle)
}