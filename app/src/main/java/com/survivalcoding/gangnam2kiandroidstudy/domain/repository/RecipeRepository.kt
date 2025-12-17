package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>
    suspend fun getSavedRecipes(): List<Recipe>
    suspend fun getRecipe(recipeId: Long): Recipe?
    suspend fun isRecipeSaved(recipeId: Long): Boolean
    suspend fun setRecipe(recipe: Recipe)
}


