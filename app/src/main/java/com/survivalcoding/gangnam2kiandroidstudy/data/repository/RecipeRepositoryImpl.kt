package com.survivalcoding.gangnam2kiandroidstudy.data.repository

import android.util.Log
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
        Log.d("RecipeRepositoryImpl", "setRecipe: $recipe")
        // TODO: 로컬 저장소(Room DB 등)에 레시피 저장 구현 필요
        // 현재는 no-op으로 처리
    }
}
