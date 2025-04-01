package com.bvn.newsapp.presentation.details

import com.bvn.newsapp.domain.model.Article

sealed class DetailsEvent {
    object onBackClicked: DetailsEvent()
    object onBookmarkClicked: DetailsEvent()
    object onBrowsingClick: DetailsEvent()
    data class upsertDeleteArticle(val article: Article) : DetailsEvent()
    object removeSideEffect: DetailsEvent()
}