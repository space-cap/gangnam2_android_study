package com.survivalcoding.gangnam2kiandroidstudy.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.RecipeRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class HomeViewModel(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()


    init {
        // ViewModel이 생성될 때 레시피 목록을 가져옵니다.
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val recipes = recipeRepository.getRecipes()
                _uiState.update {
                    it.copy(
                        recipes = recipes,
                        filteredRecipes = recipes,
                        isLoading = false,
                    )
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Failed to load recipes", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessages = e.message,
                    )
                }
            }
        }

        // 검색어 변경 시 debounce 적용하여 필터링
        uiState
            .map { it.searchText }
            .debounce(DEBOUNCE_TIMEOUT_MILLIS)
            .distinctUntilChanged()
            .onEach { fetchRecipes() }
            .launchIn(viewModelScope)
    }


    private fun fetchRecipes() {
        val currentState = _uiState.value
        val filtered = currentState.recipes.filter { recipe ->
            // 카테고리 필터링
            val matchesCategory = currentState.selectedCategory.isEmpty() ||
                    currentState.selectedCategory == "All" ||
                    recipe.category.equals(currentState.selectedCategory, ignoreCase = true)

            // 검색어 필터링
            val matchesSearch = currentState.searchText.isEmpty() ||
                    recipe.name.contains(currentState.searchText, ignoreCase = true)

            matchesCategory && matchesSearch
        }

        _uiState.update {
            it.copy(filteredRecipes = filtered)
        }
    }

    fun changeCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
        // 카테고리 변경 시 즉시 필터링
        fetchRecipes()
    }

    fun changeSearchText(searchText: String) {
        _uiState.update {
            it.copy(searchText = searchText)
        }
        // Flow에서 debounce 처리하므로 여기서는 상태만 업데이트
    }

    companion object {
        const val DEBOUNCE_TIMEOUT_MILLIS = 500L
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Application 인스턴스를 가져와서 recipeRepository를 얻습니다.
                val recipeRepository = (this[APPLICATION_KEY] as AppApplication).recipeRepository
                // HomeViewModel 인스턴스를 생성하여 반환합니다.
                HomeViewModel(
                    recipeRepository,
                )
            }
        }
    }
}
