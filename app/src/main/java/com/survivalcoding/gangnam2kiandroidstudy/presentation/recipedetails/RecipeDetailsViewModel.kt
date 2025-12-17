package com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.domain.usercase.GetRecipeDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
    recipeDetailsState: RecipeDetailsState = RecipeDetailsState(),
) : ViewModel() {
    private val _uiState: MutableStateFlow<RecipeDetailsState> =
        MutableStateFlow(recipeDetailsState)
    val uiState: StateFlow<RecipeDetailsState> = _uiState.asStateFlow()

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

    companion object {
        fun factory(application: AppApplication): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RecipeDetailsViewModel(
                    application.getRecipeDetailsUseCase,
                )
            }
        }
    }
}
