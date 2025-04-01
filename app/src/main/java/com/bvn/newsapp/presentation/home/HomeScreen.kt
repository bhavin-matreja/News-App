package com.bvn.newsapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.bvn.newsapp.R
import com.bvn.newsapp.domain.model.Article
import com.bvn.newsapp.presentation.Dimens.MediumPadding1
import com.bvn.newsapp.presentation.common.ArticlesList
import com.bvn.newsapp.presentation.common.SearchBar
import com.bvn.newsapp.presentation.navgraph.Route

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit,
) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = " \uD83D\uDFE5 ") { it.title }
            } else {
                ""
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {
        Text(
            text = "News",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = MediumPadding1)
        )
        Spacer(modifier = Modifier.height(10.dp))
        SearchBar(
            text = "",
            readOnly = true,
            onValueChange = {},
            onSearch = navigateToSearch,
            onClick = {},
            modifier = Modifier.padding(horizontal = MediumPadding1)
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(
            text = titles, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding1)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        ArticlesList(
            articles = articles,
            onClick = { navigateToDetails(it) },
            modifier = Modifier.padding(horizontal = MediumPadding1)
        )

    }

}