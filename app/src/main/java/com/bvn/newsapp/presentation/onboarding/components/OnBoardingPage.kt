package com.bvn.newsapp.presentation.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.bvn.newsapp.R
import com.bvn.newsapp.presentation.Dimens
import com.bvn.newsapp.presentation.Dimens.MediumPadding2
import com.bvn.newsapp.presentation.onboarding.Page
import com.bvn.newsapp.presentation.onboarding.pages
import com.bvn.newsapp.ui.theme.NewsAppTheme

@Composable
fun OnBoardingPage(
    page: Page,
    modifier: Modifier = Modifier
) {
     Column(modifier = modifier) {
        Image(
            painter = painterResource(id = page.image),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(Dimens.MediumPadding1))
        Text(
            modifier = Modifier.padding(horizontal = MediumPadding2),
            text = page.title,
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.display_small)
        )
        Text(
            modifier = Modifier.padding(horizontal = MediumPadding2),
            text = page.description,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.text_medium)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewOnBoardingPage() {
    NewsAppTheme {
        OnBoardingPage(page = pages[0])
    }
}