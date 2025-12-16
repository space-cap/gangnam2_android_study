package com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe

data class SearchRecipesState(
    val searchText: String = "", // 검색어
    val isLoading: Boolean = false, // 로딩 중인지 아닌지
    val recipes: List<Recipe> = emptyList(), // 전체 레시피 목록
    val filteredRecipes: List<Recipe> = emptyList(), // 검색어에 따른 필터링된 레시피 목록
    val searchFilter: RecipeFilterState = RecipeFilterState(),
)
