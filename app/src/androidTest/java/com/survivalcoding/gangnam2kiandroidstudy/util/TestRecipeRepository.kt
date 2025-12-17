package com.survivalcoding.gangnam2kiandroidstudy.util

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository

class TestRecipeRepository(private val initialRecipes: List<Recipe>) : RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> {
        return initialRecipes
    }

    override suspend fun getSavedRecipes(): List<Recipe> {
        return emptyList()  // 또는 테스트용 저장된 레시피 목록 반환
    }

    override suspend fun getRecipeById(recipeId: Long): Recipe? {
        return initialRecipes.find { it.id == recipeId }
    }

    override suspend fun isRecipeSaved(recipeId: Long): Boolean {
        return false  // 또는 테스트 시나리오에 맞는 로직
    }

    override suspend fun setRecipe(recipe: Recipe) {
        // 테스트용 구현: 필요시 내부 리스트 업데이트
    }
}
