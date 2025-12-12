package com.survivalcoding.gangnam2kiandroidstudy.util

import com.survivalcoding.gangnam2kiandroidstudy.data.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.RecipeRepository

class TestRecipeRepository(private val initialRecipes: List<Recipe>) : RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> {
        return initialRecipes
    }
}
