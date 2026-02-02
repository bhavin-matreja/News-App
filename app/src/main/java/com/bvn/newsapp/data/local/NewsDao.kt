package com.bvn.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bvn.booksappcompose.data.local.entity.NewsArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: NewsArticleEntity)

    @Delete
    suspend fun delete(article: NewsArticleEntity)

    @Query("SELECT * FROM articles")
    fun getArticles(): Flow<List<NewsArticleEntity>>

    @Query("SELECT * FROM articles WHERE url=:url")
    suspend fun getArticle(url: String): NewsArticleEntity?
}