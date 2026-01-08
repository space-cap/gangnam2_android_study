package com.survivalcoding.gangnam2kiandroidstudy.data.repository

import android.util.Log
import com.survivalcoding.gangnam2kiandroidstudy.data.datasource.RecipeDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.mapper.toRecipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource,
    private val bookmarkRepository: BookmarkRepository,
) : RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> {
        return dataSource.getRecipes().recipes?.map { it.toRecipe() } ?: emptyList()
    }

    override suspend fun getSavedRecipes(): List<Recipe> {
        return emptyList()
    }

    override suspend fun getRecipe(recipeId: Long): Recipe? {
        return getRecipes().find { it.id == recipeId }
    }

    override suspend fun isRecipeSaved(recipeId: Long): Boolean {
        return bookmarkRepository.isBookmarked(recipeId)
    }

    override suspend fun setRecipe(recipe: Recipe) {
        Log.d("RecipeRepositoryImpl", "setRecipe: $recipe")
        // TODO: 로컬 저장소(Room DB 등)에 레시피 저장 구현 필요
        // 현재는 no-op으로 처리
    }

    override suspend fun toggleBookmark(recipe: Recipe) {
        Log.d("RecipeRepositoryImpl", "Toggling bookmark for recipeId: ${recipe.id}")
        bookmarkRepository.toggleBookmark(recipe.id)
    }

    override fun getBookmarkedRecipeIds(): Flow<Set<Int>> {
        return emptyFlow<Set<Int>>()
    }
}
