package com.survivalcoding.gangnam2kiandroidstudy.util

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository

class TestRecipeRepository(private val initialRecipes: List<Recipe>) : RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> {
        return initialRecipes
    }

    override suspend fun getSavedRecipes(): List<Recipe> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeById(recipeId: Long): Recipe? {
        TODO("Not yet implemented")
    }

    override suspend fun isRecipeSaved(recipeId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }
}
