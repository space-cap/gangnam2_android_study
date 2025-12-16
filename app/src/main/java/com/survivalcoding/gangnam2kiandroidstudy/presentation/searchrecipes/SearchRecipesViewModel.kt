package com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
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
class SearchRecipesViewModel(
    private val recipeRepository: RecipeRepository,

    ) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchRecipesState())
    val uiState = _uiState.asStateFlow()
    private val searchTextFlow: Flow<String> = uiState.map { it.searchText }

    init {
        searchTextFlow
            .debounce(DEBOUNCE_TIMEOUT_MILLIS)
            .distinctUntilChanged()
            .onEach { fetchRecipes(searchText = it) }
            .launchIn(viewModelScope)

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
                _uiState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun fetchRecipes(
        searchText: String = uiState.value.searchText,
        searchFilter: RecipeFilterState = uiState.value.searchFilter,
    ) {
        viewModelScope.launch {
            setLoading(true)
            try {
                val allRecipes = uiState.value.recipes
                val filtered = if (searchText.isBlank()) {
                    allRecipes
                } else {
                    allRecipes.filter { recipe ->
                        recipe.name.contains(searchText, ignoreCase = true) ||
                                recipe.chef.contains(searchText, ignoreCase = true)
                    }
                }
                _uiState.update { it.copy(filteredRecipes = filtered) }
            } finally {
                setLoading(false)
            }
        }

    }

    fun changeSearchText(searchText: String) {
        _uiState.update {
            it.copy(searchText = searchText)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }

    companion object {
        const val DEBOUNCE_TIMEOUT_MILLIS = 500L

        /**
         * SearchRecipesViewModel 인스턴스를 생성하는 ViewModelProvider.Factory 입니다.
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                // Application 인스턴스를 가져와서 recipeRepository를 얻습니다.
                val recipeRepository = (this[APPLICATION_KEY] as AppApplication).recipeRepository
                // SearchRecipesViewModel 인스턴스를 생성하여 반환합니다.
                SearchRecipesViewModel(
                    recipeRepository,

                    )
            }
        }
    }
}
