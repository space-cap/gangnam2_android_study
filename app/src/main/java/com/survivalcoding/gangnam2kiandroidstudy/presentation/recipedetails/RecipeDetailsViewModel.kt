package com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.usercase.GetRecipeDetailsUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.usercase.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    recipeDetailsState: RecipeDetailsState = RecipeDetailsState(),
) : ViewModel() {
    private val _uiState: MutableStateFlow<RecipeDetailsState> =
        MutableStateFlow(recipeDetailsState)
    val uiState: StateFlow<RecipeDetailsState> = _uiState.asStateFlow()

    fun onAction(action: RecipeDetailsAction) {
        when (action) {
            RecipeDetailsAction.OnBackClick -> {
                // 뒤로가기 동작 구현
            }

            is RecipeDetailsAction.OnTabClick -> {
                changeTab(action.tabIndex)
            }

            RecipeDetailsAction.OnMenuClick -> {
                // 메뉴 클릭 동작 구현
                showMenu()
            }
            RecipeDetailsAction.OnMenuDismissRequest -> {
                // 메뉴 닫기 동작 구현
                hideMenu()
            }
            RecipeDetailsAction.OnShareClick -> {
                // 공유 클릭 동작 구현
                showShareDialog()
            }
            RecipeDetailsAction.OnShareDismissRequest -> {
                // 공유 닫기 동작 구현
                hideShareDialog()
            }
            is RecipeDetailsAction.OnCopyClick -> {
                // 텍스트 복사 동작 구현
            }
            RecipeDetailsAction.OnRateClick -> {
                // 평가 클릭 동작 구현
            }
            is RecipeDetailsAction.OnReviewClick -> {
                // 리뷰 클릭 동작 구현
            }
            is RecipeDetailsAction.OnBookmarkClick -> {
                // 북마크 클릭 동작 구현
                viewModelScope.launch {
                    _uiState.value.recipe?.let { currentRecipe ->
                        val newState = !currentRecipe.isSaved
                        Log.d("RecipeDetailsVM", "Bookmark toggled for recipeId: ${currentRecipe.id}. New state: $newState")
                        toggleBookmarkUseCase(currentRecipe)
                        _uiState.update {
                            it.copy(recipe = currentRecipe.copy(isSaved = newState))
                        }
                    }
                }
            }
        }
    }

    fun fetchRecipeDetails(recipeId: Long) {
        setLoading(true)
        viewModelScope.launch {
            val result = getRecipeDetailsUseCase(recipeId)
            _uiState.update {
                it.copy(
                    recipe = result.recipe,
                    profile = result.profile,
                    ingredients = result.ingredients,
                    procedures = result.procedures,
                )
            }
        }
        setLoading(false)
    }

    fun changeTab(selectedIndex: Int) {
        _uiState.update { it.copy(selectedTabIndex = selectedIndex) }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

    private fun showMenu() {
        _uiState.update {
            it.copy(isMenuVisible = true)
        }
    }

    private fun hideMenu() {
        _uiState.update {
            it.copy(isMenuVisible = false)
        }
    }

    private fun showShareDialog() {
        _uiState.update {
            it.copy(isShareDialogVisible = true)
        }
        hideMenu()
    }

    private fun hideShareDialog() {
        _uiState.update {
            it.copy(isShareDialogVisible = false)
        }
    }
}
