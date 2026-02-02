package com.bvn.newsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.presentation.Dimens
import com.bvn.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.bvn.newsapp.presentation.Dimens.MediumPadding1

@Composable
fun ArticleList(
    articles: LazyPagingItems<NewsArticle>,
    onArticleClicked: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    val loadState = articles.loadState
    when (loadState.refresh) {
        is LoadState.Loading -> {
            ShimmerEffect()
        }

        is LoadState.Error -> {
            EmptyScreen(error = loadState.refresh as LoadState.Error)
        }

        is LoadState.NotLoading -> {
            if (articles.itemCount == 0) {
                EmptyScreen()
                return
            }
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Dimens.MediumPadding1),
                contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding2)
            ) {
                items(articles.itemCount) {
                    articles[it]?.let {
                        ArticleCard(
                            newsArticle = it,
                            onClick = { onArticleClicked(it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles: List<NewsArticle>,
    onClick: (NewsArticle) -> Unit
) {
    if (articles.isEmpty()) {
        EmptyScreen()
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
        contentPadding = PaddingValues(all = ExtraSmallPadding2)
    ) {
        items(articles.size) {
            articles[it].let {
                ArticleCard(newsArticle = it, onClick = { onClick(it) })
            }
        }
    }
}

@Composable
private fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10) {
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}