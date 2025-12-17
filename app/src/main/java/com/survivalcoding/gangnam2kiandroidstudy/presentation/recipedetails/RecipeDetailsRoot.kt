package com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication

/**
 * 레시피 상세 정보 화면의 루트 컴포저블
 *
 * @param id 레시피 아이디
 * @param modifier Modifier
 * @param viewModel RecipeDetailsViewModel
 * @param onBackClick 뒤로가기 버튼 클릭 시 호출될 콜백
 */
@Composable
fun RecipeDetailsRoot(
    id: Long,
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailsViewModel = viewModel(
        factory = RecipeDetailsViewModel.factory(
            LocalContext.current.applicationContext as AppApplication,
        ),
    ),
    onBackClick: () -> Unit = {},
) {
    // UI 상태를 수집
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // id가 변경될 때마다 레시피 상세 정보를 다시 가져옴
    // LaunchedEffect는 컴포저블이 화면에 처음 나타날 때(Composition) 코드를 실행하고,
    // 화면에서 사라질 때(Discomposition) 실행을 취소합니다.
    // key로 전달된 id가 변경될 때마다 내부 코드가 재실행됩니다.
    LaunchedEffect(id) {
        viewModel.fetchRecipeDetails(id)
    }

    // 레시피 상세 정보 화면을 표시
    RecipeDetailsScreen(
        modifier = modifier,
        uiState = uiState,
        onTabClick = viewModel::changeTab,
        onBackClick = onBackClick,
    )
}
