package com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails

sealed interface RecipeDetailsAction {
    data object OnBackClick : RecipeDetailsAction
    data class OnTabClick(val tabIndex: Int) : RecipeDetailsAction
    data object OnMenuClick : RecipeDetailsAction
    data object OnMenuDismissRequest : RecipeDetailsAction
    data object OnShareClick : RecipeDetailsAction
    data object OnShareDismissRequest : RecipeDetailsAction
    data class OnCopyClick(val text: String) : RecipeDetailsAction
    data object OnRateClick : RecipeDetailsAction
    data class OnReviewClick(val recipeId: Long) : RecipeDetailsAction
    data class OnBookmarkClick(val recipeId: Long) : RecipeDetailsAction
}
