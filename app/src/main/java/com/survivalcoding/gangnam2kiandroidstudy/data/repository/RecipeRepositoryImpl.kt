package com.survivalcoding.gangnam2kiandroidstudy.data.repository

import com.survivalcoding.gangnam2kiandroidstudy.data.datasource.RecipeDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.mapper.toRecipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource
) : RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> {
        return dataSource.getRecipes().recipes?.map { it.toRecipe() } ?: emptyList()
    }

    override suspend fun getSavedRecipes(): List<Recipe> {
        return emptyList()
    }

    override suspend fun getRecipeById(recipeId: Long): Recipe? {
        return getRecipes().find { it.id == recipeId }
    }

    override suspend fun isRecipeSaved(recipeId: Long): Boolean {
        return getSavedRecipes().any { it.id == recipeId }
    }

    override suspend fun setRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }
}
