package com.bvn.newsapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.presentation.Dimens.MediumPadding1
import com.bvn.newsapp.presentation.common.ArticleList
import com.bvn.newsapp.presentation.common.EmptyScreen
import com.bvn.newsapp.presentation.common.SearchBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    articles: LazyPagingItems<NewsArticle>,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (NewsArticle) -> Unit
) {
    // Scaffold provides the correct 'innerPadding' that accounts for the Status Bar
    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier.statusBarsPadding().padding(horizontal = MediumPadding1),
                text = state.searchQuery,
                readOnly = false,
                onValueChanged = {
                    event(SearchEvent.UpdateSearchQuery(it))
                },
                onSearch = {
                    event(SearchEvent.SearchNews)
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(
                    top = MediumPadding1,
                    start = MediumPadding1,
                    end = MediumPadding1
                )
        ) {

            Spacer(modifier = Modifier.height(MediumPadding1))
            if (state.searchQuery.isEmpty()) {
                EmptyScreen()
            } else {
                ArticleList(
                    articles = articles,
                    onArticleClicked = { navigateToDetails(it) }
                )
            }
        }
    }
}