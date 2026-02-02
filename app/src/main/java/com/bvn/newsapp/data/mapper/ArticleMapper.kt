package com.bvn.newsapp2.data.mapper

import com.bvn.newsapp.data.remote.dto.Article
import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.domain.model.NewsSource


fun Article.toNewsArticle(): NewsArticle {
    return NewsArticle(
        author = this.author.orEmpty(),
        content = this.content.orEmpty(),
        description = this.description.orEmpty(),
        publishedAt = this.publishedAt.orEmpty(),
        source = NewsSource(this.source?.id.orEmpty(), this.source?.name.orEmpty()),
        title = this.title.orEmpty(),
        url = this.url.orEmpty(),
        urlToImage = this.urlToImage.orEmpty()
    )
}