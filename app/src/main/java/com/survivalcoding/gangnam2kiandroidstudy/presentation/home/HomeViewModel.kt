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
    }


    fun changeCategory(category: String) {
        _uiState.update {
            val filtered = if (category == "All" || category.isEmpty()) {
                it.recipes
            } else {
                it.recipes.filter { recipe ->
                    recipe.category == category
                }
            }.filter { recipe ->
                recipe.name.contains(it.searchText, ignoreCase = true)
            }
            it.copy(
                selectedCategory = category,
                filteredRecipes = filtered
            )
        }
    }

    fun changeSearchText(searchText: String) {
        _uiState.update {
            val filtered = it.recipes.filter { recipe ->
                recipe.name.contains(searchText, ignoreCase = true)
            }.filter { recipe ->
                it.selectedCategory.isEmpty() ||
                        it.selectedCategory == "All" ||
                        recipe.category == it.selectedCategory
            }
            it.copy(
                searchText = searchText,
                filteredRecipes = filtered
            )
        }
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
