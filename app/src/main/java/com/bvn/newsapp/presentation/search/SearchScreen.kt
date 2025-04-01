package com.bvn.newsapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.bvn.newsapp.domain.model.Article
import com.bvn.newsapp.presentation.Dimens.MediumPadding1
import com.bvn.newsapp.presentation.common.ArticlesList
import com.bvn.newsapp.presentation.common.SearchBar
import com.bvn.newsapp.presentation.navgraph.Route

@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1,
                start = MediumPadding1,
                end = MediumPadding1
            )
            .statusBarsPadding()
    ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { onEvent(SearchEvent.UpdateSearchQuery(it)) },
            onClick = {},
            onSearch = { onEvent(SearchEvent.searchNews) })
        Spacer(modifier = Modifier.height(MediumPadding1))
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(
                articles = articles,
                onClick = { navigateToDetails(it) }
            )
        }
    }
}