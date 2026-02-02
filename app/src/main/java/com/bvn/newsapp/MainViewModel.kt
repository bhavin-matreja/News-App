package com.bvn.newsapplicationnew

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bvn.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.bvn.newsapp.presentation.navgraph.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.Route
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    private val _splashCondition = MutableStateFlow(true)
    val splashCondition = _splashCondition.asStateFlow()

    private val _startDestination = MutableStateFlow<Routes>(Routes.OnBoardingScreen)
    var startDestination = _startDestination.asStateFlow()

    init {
        appEntryUseCases.readAppEntry().onEach { isOnBoardingScreenShown ->
            if (isOnBoardingScreenShown) {
                _startDestination.value = Routes.NewsNavigator
            } else {
                _startDestination.value = Routes.OnBoardingScreen
            }
            delay(300)
            _splashCondition.value = false
        }.launchIn(viewModelScope)
    }
}