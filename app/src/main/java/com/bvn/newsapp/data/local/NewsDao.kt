package com.bvn.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bvn.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Unit

    @Delete
    suspend fun delete(article: Article): Unit

    @Query("SELECT * FROM Article")
    fun getArticles(): Flow<List<Article>>

    @Query("SELECT * FROM ARTICLE WHERE url=:url")
    suspend fun getArticle(url: String): Article?
}