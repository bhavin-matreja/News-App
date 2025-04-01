package com.bvn.newsapp.presentation.navgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.bvn.newsapp.presentation.bookmark.BookmarkScreen
import com.bvn.newsapp.presentation.bookmark.BookmarkViewModel
import com.bvn.newsapp.presentation.home.HomeScreen
import com.bvn.newsapp.presentation.home.HomeViewModel
import com.bvn.newsapp.presentation.news_navigator.NewsNavigatorScreen
import com.bvn.newsapp.presentation.onboarding.OnBoardingScreen
import com.bvn.newsapp.presentation.onboarding.OnBoardingViewModel
import com.bvn.newsapp.presentation.search.SearchScreen
import com.bvn.newsapp.presentation.search.SearchViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route,
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(viewModel::onEvent)
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ) {
            composable(route = Route.NewsNavigatorScreen.route) {
                NewsNavigatorScreen()

                /*val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(articles = articles, navigate = {} )*/

                /*val viewModel: SearchViewModel = hiltViewModel()
                SearchScreen(
                    state = viewModel.state,
                    onEvent = viewModel::onEvent,
                    navigate = {}
                )

                val viewMode: BookmarkViewModel = hiltViewModel()
                BookmarkScreen(state = viewMode.state, navigate = {}) */
            }
        }
    }
}