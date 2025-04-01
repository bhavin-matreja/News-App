package com.bvn.newsapp.presentation.onboarding

sealed class OnBoardingEvent {
    object SaveAppEntry: OnBoardingEvent()
}