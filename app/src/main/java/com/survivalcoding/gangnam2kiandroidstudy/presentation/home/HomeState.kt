package com.survivalcoding.gangnam2kiandroidstudy.presentation.home

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe

data class HomeState(
    val searchText: String = "", // 검색어
    val recipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(), // 검색어에 따른 필터링된 레시피 목록
    val isLoading: Boolean = false,
    val selectedCategory: String = "All",
    val newRecipes: List<Recipe> = emptyList(),
    val errorMessages: String? = "",
)
