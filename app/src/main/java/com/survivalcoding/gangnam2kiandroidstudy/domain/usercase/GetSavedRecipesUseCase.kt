package com.survivalcoding.gangnam2kiandroidstudy.domain.usercase

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.first

/**
 * 저장된 모든 레시피 목록을 가져오고, 각 레시피가 북마크되었는지 여부를 설정하는 유즈케이스입니다.
 * 유즈케이스는 애플리케이션의 비즈니스 로직을 캡슐화합니다.
 *
 * @property recipeRepository 레시피 데이터에 접근하기 위한 저장소입니다.
 * @property bookmarkRepository 북마크 데이터에 접근하기 위한 저장소입니다.
 */
class GetSavedRecipesUseCase(
    private val recipeRepository: RecipeRepository,
    private val bookmarkRepository: BookmarkRepository
) {
    /**
     * 유즈케이스를 실행하는 함수입니다. `operator fun invoke`를 사용하면 클래스 인스턴스를 함수처럼 호출할 수 있습니다.
     * 예: `getSavedRecipesUseCase()`
     *
     * @return 북마크 상태가 업데이트된 레시피 목록
     */
    suspend operator fun invoke(): List<Recipe> {
        // 1. 레시피 저장소에서 모든 레시피를 가져옵니다.
        val savedRecipes = recipeRepository.getRecipes()

        // 2. 북마크 저장소에서 북마크된 레시피 ID 목록을 한 번만 가져옵니다.
        // getBookmarkedRecipeIds()는 Flow를 반환하는데, 데이터베이스가 변경될 때마다 새로운 목록을 방출합니다.
        // .first()를 사용하여 현재 시점의 북마크 목록만 가져옵니다. 이렇게 하지 않으면 collect가 끝나지 않아 함수가 반환되지 않을 수 있습니다.
        val bookmarkedRecipeIds = bookmarkRepository.getBookmarkedRecipeIds().first()

        // 3. 각 레시피를 순회하며 북마크 상태가 업데이트된 새 리스트를 생성합니다.
        return savedRecipes.map { recipe ->
            // 4. 북마크 목록에 현재 레시피 ID가 포함되어 있는지 확인하여 isBookmarked 값을 설정한 복사본을 반환합니다.
            recipe.copy(isBookmarked = bookmarkedRecipeIds.contains(recipe.id))
        }
    }
}
