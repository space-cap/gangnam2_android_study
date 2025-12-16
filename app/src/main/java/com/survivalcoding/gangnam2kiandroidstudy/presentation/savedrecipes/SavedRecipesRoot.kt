package com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SavedRecipesRoot(
    modifier: Modifier = Modifier,
    onItemClick: (Long) -> Unit = {},
) {
    // 현재 컨텍스트에서 applicationContext를 가져옵니다.
    val context = LocalContext.current
    val application = context.applicationContext as Application

    // 위에서 수정한 Factory를 사용하여 ViewModel을 생성합니다.
    val viewModel: SavedRecipesViewModel = viewModel(factory = SavedRecipesViewModelFactory(application))
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SavedRecipesScreen(
        uiState = state,
        onClick = onItemClick,
    )
}

@Preview(showBackground = true)
@Composable
fun SavedRecipesRootPreview() {
    SavedRecipesScreen()
}
