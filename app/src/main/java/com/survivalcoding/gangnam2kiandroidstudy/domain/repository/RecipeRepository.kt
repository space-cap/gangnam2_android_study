package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>
    suspend fun getSavedRecipes(): List<Recipe>
    suspend fun getRecipe(recipeId: Long): Recipe?
    suspend fun isRecipeSaved(recipeId: Long): Boolean
    suspend fun setRecipe(recipe: Recipe)

    /**
     * 특정 레시피의 북마크 상태를 토글합니다.
     * @param recipe 북마크 상태를 변경할 레시피 객체
     */
    suspend fun toggleBookmark(recipe: Recipe)

    /**
     * 북마크된 모든 레시피의 ID 목록을 Flow 형태로 관찰합니다.
     * UI 상태가 데이터 변경에 따라 자동으로 업데이트되도록 할 수 있습니다.
     * @return 북마크된 레시피 ID의 Set을 방출하는 Flow
     */
    fun getBookmarkedRecipeIds(): Flow<Set<Int>>
}


