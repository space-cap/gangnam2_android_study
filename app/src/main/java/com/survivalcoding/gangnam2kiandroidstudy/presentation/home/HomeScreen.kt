package com.survivalcoding.gangnam2kiandroidstudy.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.DishCard
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.NewRecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCategorySelector
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.SearchBar
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.AppTextStyles

@Composable
fun HomeScreen(
    state: HomeState = HomeState(),
    onAction: (HomeAction) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 64.dp,
                    start = 30.dp,
                    bottom = 30.dp,
                    end = 30.dp,
                ),
        ) {
            Column(
            ) {
                Text(
                    text = "Hello Lee",
                    style = AppTextStyles.largeTextBold,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "What are you cooking today?",
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.gray3,
                )
            }

            Image(
                painter = painterResource(R.drawable.ic_profile_outline),
                contentDescription = "프로필 이미지",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
                    .background(
                        color = AppColors.secondary40,
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        }

        // 검색창
        SearchBar(
            modifier = Modifier.padding(horizontal = 30.dp),
            query = state.searchText,
            onClick = { onAction(HomeAction.OnSearchClick) },
            onQueryChange = { },
            onFilterClick = { },
        )

        // 카테고리 선택바
        RecipeCategorySelector(
            modifier = Modifier.padding(vertical = 15.dp),
            selectedCategory = state.selectedCategory,
            onCategoryClick = { onAction(HomeAction.OnCategoryClick(it)) },
        )

        // dish cards
        LazyRow() {
            items(
                state.filteredRecipes,
                key = { it.id }
            ) { item ->
                DishCard(
                    recipe = item,
                    onDishClick = { recipe ->
                        onAction(HomeAction.OnRecipeClick(recipe.id))
                    },
                    onBookmarkClick = { recipe ->
                        onAction(HomeAction.OnBookmarkClick(recipe))
                    },
                    isSaved = state.savedRecipeIds.contains(item.id.toInt()),
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .width(150.dp)
                )
            }
        }

        // New Recipes
        Text(
            text = "New Recipes",
            style = AppTextStyles.normalTextBold,
            modifier = Modifier.padding(
                top = 20.dp,
                start = 30.dp,
                end = 5.dp,
            )
        )


        if (state.isNewRecipesLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(127.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("Loading...")
            }
        } else if (state.newRecipes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(127.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text("데이터가 없습니다.")
            }
        } else {

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                items(
                    items = state.newRecipes,
                    key = { it.id },
                ) {
                    NewRecipeCard(
                        recipe = it,
                        onClick = { recipeId ->
                            onAction(HomeAction.OnRecipeClick(recipeId))
                        },
                    )
                }
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(
            filteredRecipes = listOf(
                Recipe(
                    id = 1,
                    name = "Traditional Spare Ribs",
                    image = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
                    chef = "Chef John",
                    time = "20 min",
                    rating = 4.5,
                    category = "Korean"
                ),
                Recipe(
                    id = 2,
                    name = "Spice Roasted Chicken",
                    image = "https://cdn.pixabay.com/photo/2018/12/04/16/49/tandoori-3856045_1280.jpg",
                    chef = "Chef Mark",
                    time = "30 min",
                    rating = 4.8,
                    category = "Italian",
                )
            )
        ),
        onAction = {},
    )
}
