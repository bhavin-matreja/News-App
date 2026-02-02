package com.bvn.newsapp.presentation.details

import com.bvn.newsapp.domain.model.NewsArticle

sealed interface DetailsEvent {
    object OnBackClicked: DetailsEvent
    object onBrowsingClicked: DetailsEvent
    data class UpsertDeleteArticle(val article: NewsArticle) : DetailsEvent
}