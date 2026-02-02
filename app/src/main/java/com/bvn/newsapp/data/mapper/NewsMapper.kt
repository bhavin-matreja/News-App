package com.bvn.booksappcompose.data.mapper

import com.bvn.booksappcompose.data.local.entity.NewsArticleEntity
import com.bvn.booksappcompose.data.local.entity.NewsSourceEntity
import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.domain.model.NewsSource

fun NewsArticleEntity.toDomain(): NewsArticle {
    return NewsArticle(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = NewsSource(source.id, source.name),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun NewsArticle.toEntity(): NewsArticleEntity {
    return NewsArticleEntity(
        url = url,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = NewsSourceEntity(source.id, source.name),
        title = title,
        urlToImage = urlToImage
    )
}