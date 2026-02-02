package com.bvn.newsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bvn.newsapp.R
import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.domain.model.NewsSource
import com.bvn.newsapp.presentation.Dimens
import com.bvn.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.bvn.newsapp.ui.theme.NewsAppTheme
import com.bvn.newsapp.util.formatDate

@Composable
fun ArticleCard(
    newsArticle: NewsArticle,
    onClick: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(modifier = modifier.clickable {
        onClick(newsArticle)
    }) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(newsArticle.urlToImage)
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .size(Dimens.ArticleCardSize),
            error = painterResource(R.drawable.ic_network_error)
        )
        Column(
            verticalArrangement = Arrangement.SpaceAround, // Evenly spaces title and metadata
            modifier = Modifier
                .padding(start = Dimens.ExtraSmallPadding)
                .fillMaxHeight()
                .height(Dimens.ArticleCardSize) // Match the image height for vertical alignment
                .weight(1f),
        ) {
            Text(
                text = newsArticle.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = newsArticle.source.name,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time), // Use a time icon
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.SmallIconSize),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Text(
                    text = newsArticle.publishedAt.formatDate(),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.body),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewArticleCard() {
    NewsAppTheme {
        ArticleCard(newsArticle = NewsArticle(
            "author",
            "content",
            "desc",
            "2025-03-31T13:06:00Z",
            NewsSource("id", "name"),
            "title",
            "url",
            "urlToImage",
        ), onClick = { })
    }
}