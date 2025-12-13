package com.survivalcoding.gangnam2kiandroidstudy.presentation.home

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoot(

) {
    // 현재 컨텍스트에서 applicationContext를 가져옵니다.
    val context = LocalContext.current
    val application = context.applicationContext as? Application
        ?: error("Application context is not available")

    // 위에서 수정한 Factory를 사용하여 ViewModel을 생성합니다.
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(application))
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onCategoryClick = viewModel::changeCategory,
        onSearchQueryChange = viewModel::changeSearchText,
    )
}

@Preview(showBackground = true)
@Composable
fun HomeRootPreview() {

    HomeScreen(
        state = HomeState(),
        onCategoryClick = {},
        onSearchQueryChange = {},
    )
}
