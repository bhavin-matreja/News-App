package com.bvn.newsapp.presentation.navgraph

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object OnBoardingScreen: Routes

    @Serializable
    data object HomeScreen: Routes

    @Serializable
    data object SearchScreen: Routes

    @Serializable
    data class DetailScreen(val articleJson: String) : Routes

    @Serializable
    data object BookmarkScreen: Routes

    @Serializable
    data object NewsNavigator: Routes
}