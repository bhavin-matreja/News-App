package com.bvn.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bvn.booksappcompose.data.mapper.toDomain
import com.bvn.booksappcompose.data.mapper.toEntity
import com.bvn.newsapp.data.local.NewsDao
import com.bvn.newsapp.data.remote.NewsApi
import com.bvn.newsapp.data.remote.NewsPagingSource
import com.bvn.newsapp.data.remote.SearchNewsPagingSource
import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.domain.repository.NewsRepository
import com.bvn.newsapp2.data.mapper.toNewsArticle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
): NewsRepository {

    override fun getTopHeadlines(sources: List<String>): Flow<PagingData<NewsArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        ).flow
            .map {
                it.map {
                    it.toNewsArticle()
                }
            }
    }

    override fun searchNews(
        searchQuery: String,
        sources: List<String>
    ): Flow<PagingData<NewsArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    searchQuery = searchQuery,
                    sources = sources.joinToString(separator = ","),
                    newsApi = newsApi
                )
            }
        ).flow
            .map {
                it.map {
                    it.toNewsArticle()
                }
            }
    }

    override suspend fun getArticle(url: String): NewsArticle? {
        return newsDao.getArticle(url)?.toDomain()
    }

    override fun getAllArticles(): Flow<List<NewsArticle>> {
        return newsDao.getArticles().map { newsArticleEntity ->
            newsArticleEntity.map { it.toDomain() }
        }
    }

    override suspend fun upsertArticle(article: NewsArticle) {
        newsDao.upsert(article.toEntity())
    }

    override suspend fun deleteArticle(article: NewsArticle) {
        newsDao.delete(article.toEntity())
    }
}