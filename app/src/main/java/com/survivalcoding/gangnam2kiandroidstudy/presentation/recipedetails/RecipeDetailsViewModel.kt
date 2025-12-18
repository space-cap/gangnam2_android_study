package com.survivalcoding.gangnam2kiandroidstudy.presentation.recipedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.usercase.GetRecipeDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<RecipeDetailsState> =
        MutableStateFlow(RecipeDetailsState())
    val uiState: StateFlow<RecipeDetailsState> = _uiState.asStateFlow()

    fun fetchRecipeDetails(recipeId: Long) {

        viewModelScope.launch {
            setLoading(true)
            try {
                val result = getRecipeDetailsUseCase(recipeId)
                _uiState.update {
                    it.copy(
                        recipe = result.recipe,
                        profile = result.profile,
                        ingredients = result.ingredients,
                        procedures = result.procedures,
                    )
                }
            } finally {
                setLoading(false)
            }
        }
    }

    fun changeTab(selectedIndex: Int) {
        _uiState.update { it.copy(selectedTabIndex = selectedIndex) }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isLoading = isLoading) }
    }

}
