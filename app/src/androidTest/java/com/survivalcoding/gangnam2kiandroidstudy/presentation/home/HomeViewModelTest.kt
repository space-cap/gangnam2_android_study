package com.survivalcoding.gangnam2kiandroidstudy.presentation.home

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.usercase.ToggleBookmarkUseCase
import com.survivalcoding.gangnam2kiandroidstudy.rules.MainCoroutineRule
import com.survivalcoding.gangnam2kiandroidstudy.util.TestRecipeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var recipeRepository: TestRecipeRepository

    // UseCase들을 선언합니다.
    //private lateinit var getAllRecipesUseCase: GetAllRecipesUseCase
    private lateinit var toggleBookmarkUseCase: ToggleBookmarkUseCase
    //private lateinit var getBookmarkedRecipeIdsUseCase: GetBookmarkedRecipeIdsUseCase

    private val testRecipes = listOf(
        Recipe(
            id = 1,
            name = "Kimchi Fried Rice",
            image = "image1.jpg",
            chef = "Chef Kim",
            time = "20 min",
            rating = 4.5,
            category = "Korean"
        ),
        Recipe(
            id = 2,
            name = "Pasta Carbonara",
            image = "image2.jpg",
            chef = "Chef Mario",
            time = "30 min",
            rating = 4.8,
            category = "Italian"
        ),
        Recipe(
            id = 3,
            name = "Bulgogi",
            image = "image3.jpg",
            chef = "Chef Park",
            time = "40 min",
            rating = 4.7,
            category = "Korean"
        )
    )

    @Before
    fun setUp() {
        recipeRepository = TestRecipeRepository(testRecipes)
        toggleBookmarkUseCase = ToggleBookmarkUseCase(recipeRepository)
    }

    @Test
    fun testFetchRecipes() = runTest {
        // Given: 테스트용 레시피 데이터가 있는 repository

        // When: ViewModel을 생성하면 init 블록에서 자동으로 레시피를 로드함
        viewModel = HomeViewModel(
            recipeRepository,
            toggleBookmarkUseCase = toggleBookmarkUseCase,
        )
        advanceUntilIdle() // 모든 코루틴이 완료될 때까지 대기

        // Then: 레시피가 정상적으로 로드되고 상태가 업데이트됨
        val uiState = viewModel.uiState.value
        assertEquals(testRecipes.size, uiState.recipes.size)
        assertEquals(testRecipes.size, uiState.filteredRecipes.size)
        assertEquals(testRecipes, uiState.recipes)
        assertFalse(uiState.isLoading)
        assertEquals("All", uiState.selectedCategory)
    }

    @Test
    fun testCategoryFilter() = runTest {
        // Given: ViewModel이 초기화되고 레시피가 로드됨
        viewModel = HomeViewModel(
            recipeRepository,
            toggleBookmarkUseCase = toggleBookmarkUseCase,
        )
        advanceUntilIdle()

        // When: 카테고리를 "Korean"으로 변경
        viewModel.changeCategory("Korean")

        // Then: Korean 카테고리의 레시피만 필터링됨
        val uiState = viewModel.uiState.value
        assertEquals(2, uiState.filteredRecipes.size)
        assertEquals("Korean", uiState.selectedCategory)
        uiState.filteredRecipes.forEach { recipe ->
            assertEquals("Korean", recipe.category)
        }
    }

    @Test
    fun testSearchFilter() = runTest {
        // Given: ViewModel이 초기화되고 레시피가 로드됨
        viewModel = HomeViewModel(
            recipeRepository,
            toggleBookmarkUseCase = toggleBookmarkUseCase,
        )
        advanceUntilIdle()

        // When: 검색어를 "Pasta"로 입력
        viewModel.changeSearchText("Pasta")
        advanceUntilIdle() // debounce 대기

        // Then: "Pasta"가 포함된 레시피만 필터링됨
        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.filteredRecipes.size)
        assertEquals("Pasta Carbonara", uiState.filteredRecipes[0].name)
    }

    @Test
    fun testCombinedFilter() = runTest {
        // Given: ViewModel이 초기화되고 레시피가 로드됨
        viewModel = HomeViewModel(
            recipeRepository,
            toggleBookmarkUseCase = toggleBookmarkUseCase,
        )
        advanceUntilIdle()

        // When: 카테고리를 "Korean"으로 변경하고 검색어를 "Bulgogi"로 입력
        viewModel.changeCategory("Korean")
        viewModel.changeSearchText("Bulgogi")
        advanceUntilIdle()

        // Then: 두 조건을 모두 만족하는 레시피만 필터링됨
        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.filteredRecipes.size)
        assertEquals("Bulgogi", uiState.filteredRecipes[0].name)
        assertEquals("Korean", uiState.filteredRecipes[0].category)
    }
}
