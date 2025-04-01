package com.bvn.newsapp.domain.usecases.news

data class NewsUseCases(
    val getNewsUseCase: GetNewsUseCase,
    val searchNewsUseCase: SearchNewsUseCase,
    val deleteArticleUseCase: DeleteArticleUseCase,
    val selectArticlesUseCase: SelectArticlesUseCase,
    val upsertArticleUseCase: UpsertArticleUseCase,
    val getArticleUseCase: GetArticleUseCase
)