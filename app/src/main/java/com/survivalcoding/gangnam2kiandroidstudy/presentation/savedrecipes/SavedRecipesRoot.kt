package com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SavedRecipesRoot() {
    // 위에서 수정한 Factory를 사용하여 ViewModel을 생성합니다.
    val viewModel: SavedRecipesViewModel = viewModel(factory = SavedRecipesViewModel.Factory)
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SavedRecipesScreen(
        uiState = state,
    )
}

@Preview(showBackground = true)
@Composable
fun SavedRecipesRootPreview() {
    SavedRecipesScreen()
}