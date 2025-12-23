package com.survivalcoding.gangnam2kiandroidstudy.presentation.splash

data class SplashUiState(
    val isLoading: Boolean = true,          // 스플래시에서 작업 진행 중인지
    val isNetworkAvailable: Boolean = true, // 네트워크 연결 여부
    val errorMessage: String? = null,       // 오류 메시지
)
