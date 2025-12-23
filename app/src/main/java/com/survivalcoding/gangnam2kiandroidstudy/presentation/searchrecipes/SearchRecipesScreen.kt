package com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.FilterSearchBottomSheet
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCardMedium
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.SearchField
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles
import kotlinx.coroutines.launch


@Composable
fun SearchRecipesScreen(
    state: SearchRecipesState = SearchRecipesState(),
    onAction: (SearchRecipesAction) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // 필터 시트 출력 여부
    var showFilterSheet by remember { mutableStateOf(false) }

    val recipes = state.filteredRecipes

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = AppColors.white,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 54.dp,
                        bottom = 17.dp,
                    )
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_arrow_right),
                    contentDescription = "뒤로가기 버튼",
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(180f)
                        .align(Alignment.CenterStart)
                        .clickable { onAction(SearchRecipesAction.OnBackClick) }
                )

                Text(
                    text = stringResource(R.string.search_recipes_title),
                    style = AppTextStyles.mediumTextBold,
                    modifier = Modifier.padding(top = 10.dp, bottom = 17.dp),
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchField(
                    query = state.searchText,
                    placeholder = "Search recipe",
                    onQueryChange = {
                        onAction(SearchRecipesAction.OnSearchQueryChange(it))
                    },
                    onSearch = {},
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            // viewModel.showBottomSheet()
                            showFilterSheet = true


                        }
                        .background(
                            color = AppColors.primary100,
                        )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_setting_4_outline),
                        contentDescription = "setting icon",
                        tint = AppColors.white,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Recent Search",
                    style = AppTextStyles.mediumTextBold,
                    modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                )
            }


            // recipe items
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    contentPadding = PaddingValues(bottom = 10.dp),
                ) {
                    items(recipes) { recipe ->
                        RecipeCardMedium(
                            recipe
                        )
                    }
                }
            }


            // 필터 시트
            if (showFilterSheet) {
                FilterSearchBottomSheet(
                    onApplyFilter = { filter ->
                        onAction(SearchRecipesAction.OnFilterClick(filter))
                        showFilterSheet = false
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "필터 적용되었습니다",
                                actionLabel = "확인",
                                withDismissAction = true
                            )
                        }
                    },
                    onDismiss = {
                        showFilterSheet = false
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "필터 취소되었습니다",
                                actionLabel = "확인",
                                withDismissAction = true
                            )
                        }
                    },
                    initialFilter = state.filterSearchState,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchRecipesScreenPreview() {
    SearchRecipesScreen(
        state = SearchRecipesState(),
        onAction = {},
    )
}
