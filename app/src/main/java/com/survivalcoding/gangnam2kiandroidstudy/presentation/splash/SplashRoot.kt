package com.survivalcoding.gangnam2kiandroidstudy.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


@Composable
fun SplashRoot(
    viewModel: SplashViewModel = koinViewModel(),
    onClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SplashScreen(
        uiState = uiState,
        onClick = onClick
    )
}
