package com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saveedrecipes.SavedRecipesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchRecipesViewModel(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchRecipesState())
    val uiState = _uiState.asStateFlow()

    init {
        // ViewModel이 생성될 때 레시PI 목록을 가져옵니다.
        viewModelScope.launch {
            // 로딩 상태를 true로 설정합니다.
            _uiState.update {
                it.copy(isLoading = true)
            }

            // 리포지토리에서 레시피 목록을 가져옵니다.
            val recipes = recipeRepository.getRecipes()

            // 가져온 레시피 목록으로 UI 상태를 업데이트하고 로딩 상태를 false로 설정합니다.
            _uiState.update {
                it.copy(recipes = recipes, isLoading = false)
            }
        }
    }

    companion object {
        /**
         * SavedRecipesViewModel 인스턴스를 생성하는 ViewModelProvider.Factory 입니다.
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // SavedStateHandle 인스턴스를 생성합니다.
                val savedStateHandle = createSavedStateHandle()
                // Application 인스턴스를 가져와서 recipeRepository를 얻습니다.
                val recipeRepository = (this[APPLICATION_KEY] as AppApplication).recipeRepository
                // SavedRecipesViewModel 인스턴스를 생성하여 반환합니다.
                SavedRecipesViewModel(
                    recipeRepository,
                    savedStateHandle = savedStateHandle,
                )
            }
        }
    }
}
