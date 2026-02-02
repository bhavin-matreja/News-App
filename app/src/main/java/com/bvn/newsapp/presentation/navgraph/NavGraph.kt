package com.bvn.newsapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bvn.newsapp.presentation.news_navigator.NewsNavigator
import com.bvn.newsapp.presentation.onboarding.OnBoardingScreen
import com.bvn.newsapp.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Routes
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.OnBoardingScreen> {navBackStackEntry ->
            val viewModel: OnBoardingViewModel = hiltViewModel()
            OnBoardingScreen(
                viewModel::onEvent
            )
        }

        composable<Routes.NewsNavigator> {
            NewsNavigator()
        }
    }
}