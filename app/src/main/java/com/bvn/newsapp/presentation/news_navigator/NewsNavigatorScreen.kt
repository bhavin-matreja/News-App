package com.bvn.newsapp.presentation.news_navigator

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.bvn.newsapp.R
import com.bvn.newsapp.domain.model.NewsArticle
import com.bvn.newsapp.presentation.bookmark.BookmarkScreen
import com.bvn.newsapp.presentation.bookmark.BookmarkViewModel
import com.bvn.newsapp.presentation.details.DetailsScreen
import com.bvn.newsapp.presentation.details.DetailsViewModel
import com.bvn.newsapp.presentation.home.HomeScreen
import com.bvn.newsapp.presentation.home.HomeViewModel
import com.bvn.newsapp.presentation.navgraph.Routes
import com.bvn.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.bvn.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.bvn.newsapp.presentation.search.SearchScreen
import com.bvn.newsapp.presentation.search.SearchViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun NewsNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    val selectedItem = remember(key1 = backStackState) {
        val destination = backStackState?.destination
        when {
            destination?.hasRoute<Routes.HomeScreen>() == true -> 0
            destination?.hasRoute<Routes.SearchScreen>() == true -> 1
            destination?.hasRoute<Routes.BookmarkScreen>() == true -> 2
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.hasRoute<Routes.HomeScreen>() == true ||
                backStackState?.destination?.hasRoute<Routes.SearchScreen>() == true ||
                backStackState?.destination?.hasRoute<Routes.BookmarkScreen>() == true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTap(navController, Routes.HomeScreen)
                            1 -> navigateToTap(navController, Routes.SearchScreen)
                            2 -> navigateToTap(navController, Routes.BookmarkScreen)
                        }
                    },
                )
            }
        }
    ) { innerPadding ->
        val bottomPadding = innerPadding.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Routes.HomeScreen,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<Routes.HomeScreen> { navBackStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    articles = viewModel.news.collectAsLazyPagingItems(),
                    navigateToSearch = {
                        navController.navigate(Routes.SearchScreen)
                    },
                    navigateToDetails = {
                        navigateToDetails(navController = navController, article = it)
                    }
                )
            }

            composable<Routes.SearchScreen> {
                val viewModel: SearchViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                val articles = viewModel.articles.collectAsLazyPagingItems()
                SearchScreen(
                    state = state,
                    articles = articles,
                    event = viewModel::onEvent,
                    navigateToDetails = {
                        navigateToDetails(navController, it)
                    }
                )
            }

            composable<Routes.BookmarkScreen> {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state by viewModel.state.collectAsStateWithLifecycle()
                BookmarkScreen(
                    state = state,
                    navigateToDetails = {
                        navigateToDetails(navController, it)
                    }
                )
            }

            composable<Routes.DetailScreen> { navBackStackEntry ->

                val args = navBackStackEntry.toRoute<Routes.DetailScreen>()
                val viewModel: DetailsViewModel = hiltViewModel()
                val context = LocalContext.current

                // Collect the Flow directly inside LaunchedEffect
                LaunchedEffect(key1 = viewModel.sideEffect) {
                    viewModel.sideEffect.collect { message ->
                        if (message.isNotEmpty()) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            // Note: No need to call RemoveSideEffect anymore!
                            // Once the flow emits it, it's gone from the Channel.
                        }
                    }
                }

                /*
                val sideEffect by viewModel.sideEffect.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = sideEffect) {
                    if (sideEffect.isNotEmpty()) {
                        Toast.makeText(context, sideEffect, Toast.LENGTH_SHORT).show()
                        // viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                    }
                }

                 */
                val article = Json.decodeFromString<NewsArticle>(Uri.decode(args.articleJson))
                DetailsScreen(
                    article = article,
                    event = viewModel::onEvent,
                    navigateUp = {
                        navController.navigateUp()
                    }
                )

            }
        }
    }
}

private fun navigateToTap(navController: NavController, route: Routes) {
    navController.navigate(route) {
        navController.graph.findStartDestination().id.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: NewsArticle) {
//    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    val json = Json.encodeToString(value = article)
    // You MUST URL-encode this because JSON contains characters like '/' and '?'
    // that will break navigation routes.
    val encodedJson = Uri.encode(json)
    navController.navigate(Routes.DetailScreen(articleJson = encodedJson))

}