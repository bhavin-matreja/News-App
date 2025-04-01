package com.bvn.newsapp.presentation.navgraph

sealed class Route(val route: String) {
    object OnBoardingScreen: Route("onBoardingScreen")
    object HomeScreen: Route("homeScreen")
    object SearchScreen: Route("searchScreen")
    object BookMarkScreen: Route("bookMarkScreen")
    object DetailsScreen: Route("detailsScreen")
    object AppStartNavigation: Route("appStartNavigation")
    object NewsNavigation: Route("newsNavigation")
    object NewsNavigatorScreen: Route("newsNavigatorScreen")
}
