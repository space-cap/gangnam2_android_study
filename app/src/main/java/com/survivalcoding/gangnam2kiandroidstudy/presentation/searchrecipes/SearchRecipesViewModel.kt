package com.survivalcoding.gangnam2kiandroidstudy.presentation.searchrecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.utils.Logger
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _event = MutableSharedFlow<SearchRecipesEvent>()
    val event = _event.asSharedFlow()

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


    // TODO 다음 작업에 사용 예정
    private fun onEvent(event: SearchRecipesEvent) {
        viewModelScope.launch { _event.emit(event) }
    }


    fun onAction(
        action: SearchRecipesAction,
        navigateBack: () -> Unit,
    ) {
        when (action) {
            SearchRecipesAction.OnBackClick -> {
                navigateBack()
            }

            is SearchRecipesAction.OnSearchQueryChange -> {
                changeSearchText(action.query)
            }

            is SearchRecipesAction.OnFilterClick -> {
                applyFilter(action.filter)
            }
        }
    }

    fun applyFilter(filter: FilterSearchState) {
        Logger.d("SearchRecipesViewModel", "applyFilter: $filter")

        _uiState.update { it.copy(filterSearchState = filter) }

        val all = uiState.value.recipes
        val filtered = all
            // name 필터
            .filter { recipe ->
                recipe.name.contains(uiState.value.searchText, ignoreCase = true)
            }

            // time 필터
            .let { recipes ->
                when (filter.time) {
                    "All" -> recipes
                    "Newest" -> recipes.sortedByDescending { it.id }
                    "Oldest" -> recipes.sortedBy { it.id }
                    "Popularity" -> recipes.sortedByDescending { it.rating }
                    else -> recipes
                }

                    // rating 필터
                    .let { recipes ->
                        if (filter.rating != null) {
                            recipes.filter { it.rating >= filter.rating }
                        } else {
                            recipes
                        }
                    }

                    // category 필터
                    .let { recipes ->
                        if (filter.categories.isNotEmpty() && "All" !in filter.categories) {
                            recipes.filter { recipe ->
                                recipe.category in filter.categories
                            }
                        } else {
                            recipes
                        }
                    }
            }

        // ui 업데이트
        _uiState.update {
            it.copy(filteredRecipes = filtered)
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
    }
}
