package com.bvn.booksappcompose.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class NewsArticleEntity(
    @PrimaryKey val url: String, // DB logic
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded val source: NewsSourceEntity,
    val title: String,
    val urlToImage: String
)

data class NewsSourceEntity(val id: String, val name: String)