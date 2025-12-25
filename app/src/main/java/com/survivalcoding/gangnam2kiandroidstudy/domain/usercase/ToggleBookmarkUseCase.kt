package com.survivalcoding.gangnam2kiandroidstudy.domain.usercase

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository

class ToggleBookmarkUseCase(
    private val recipeRepository: RecipeRepository
) {
    /**
     * 레시피의 북마크 상태를 토글합니다.
     * @param recipe 북마크 상태를 변경할 레시피
     */
    suspend operator fun invoke(recipe: Recipe) {
        recipeRepository.toggleBookmark(recipe)
    }
}
