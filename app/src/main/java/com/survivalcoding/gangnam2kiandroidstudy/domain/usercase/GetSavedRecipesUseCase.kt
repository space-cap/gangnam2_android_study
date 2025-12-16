package com.survivalcoding.gangnam2kiandroidstudy.domain.usercase

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository

class GetSavedRecipesUseCase(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(): List<Recipe> {
        return recipeRepository.getRecipes()
    }
}
