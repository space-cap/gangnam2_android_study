package com.survivalcoding.gangnam2kiandroidstudy.presentation.splash

sealed interface SplashEvent {
    data object NavigateToSignIn : SplashEvent
    data class ShowSnackbar(val message: String) : SplashEvent
}
