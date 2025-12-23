package com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun SavedRecipesScreen(
    modifier: Modifier = Modifier,
    uiState: SavedRecipesState = SavedRecipesState(),
    onClick: (Long) -> Unit = {},
) {

    // SnackBar를 관리하기 위한 상태와 코루틴 스코프
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // LazyColumn의 스크롤 상태를 기억
    val listState = rememberLazyListState()

    // 스낵바 표시 여부를 추적하는 플래그
    var hasShownSnackbar by remember { mutableStateOf(false) }

    // 스크롤 상태가 변경될 때마다 호출되는 LaunchedEffect
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                // 마지막 아이템의 인덱스를 확인
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index
                val totalItemCount = listState.layoutInfo.totalItemsCount

                // 마지막 아이템이 보이고, 전체 아이템 수가 0이 아닐 때 SnackBar 표시
                if (!hasShownSnackbar && lastVisibleItemIndex != null && lastVisibleItemIndex == totalItemCount - 1 && totalItemCount > 0) {
                    hasShownSnackbar = true
                    snackbarHostState.showSnackbar("마지막 레시피입니다.")
                }
            }
    }

    Box(
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                horizontal = 20.dp,
                vertical = 20.dp
            )
        ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Saved recipes",
                style = AppTextStyles.mediumTextBold.copy(color = AppColors.black),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(10.dp))


            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.recipes) { recipe ->
                        RecipeCard(
                            recipe = recipe,
                            onClick = { onClick(recipe.id) },
                        )

                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }

        // SnackbarHost를 Box 내에 추가하고 상단 중앙에 배치
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter) // 하단 중앙에 정렬
                .padding(bottom = 106.dp) // 하단에 106.dp 패딩을 주어 위로 올림
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SavedRecipesScreenPreview() {
    SavedRecipesScreen()
}
