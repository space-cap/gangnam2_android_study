package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saveedrecipes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.data.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * 저장된 레시피 화면의 UI 상태를 나타내는 데이터 클래스입니다.
 *
 * @property recipes 저장된 레시피 목록
 * @property isLoading 데이터 로딩 중인지 여부
 */
data class RecipeUiState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
)

/**
 * 저장된 레시피 화면의 ViewModel입니다.
 *
 * @property recipeRepository 레시피 데이터를 가져오는 리포지토리
 * @property savedStateHandle ViewModel의 상태를 저장하고 복원하는 데 사용
 */
class SavedRecipesViewModel(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // UI 상태를 private MutableStateFlow로 관리합니다.
    private val _uiState = MutableStateFlow(RecipeUiState())

    // 외부에는 읽기 전용 StateFlow로 UI 상태를 노출합니다.
    val uiState = _uiState.asStateFlow()

    init {
        // ViewModel이 생성될 때 레시PI 목록을 가져옵니다.
        viewModelScope.launch {
            // 로딩 상태를 true로 설정합니다.
            _uiState.value = _uiState.value.copy(isLoading = true)
            // 리포지토리에서 레시피 목록을 가져옵니다.
            val recipes = recipeRepository.getRecipes()
            // 가져온 레시피 목록으로 UI 상태를 업데이트하고 로딩 상태를 false로 설정합니다.
            _uiState.value = _uiState.value.copy(recipes = recipes, isLoading = false)
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
