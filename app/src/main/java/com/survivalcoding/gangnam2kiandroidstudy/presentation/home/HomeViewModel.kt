package com.survivalcoding.gangnam2kiandroidstudy.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.usercase.ToggleBookmarkUseCase
import kotlinx.coroutines.FlowPreview
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
class HomeViewModel(
    private val recipeRepository: RecipeRepository,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    // eventFlow 속성 추가 (이것이 핵심입니다!)
    // 이 코드를 추가하면 HomeRoot.kt의 빨간 줄이 사라집니다.
    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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

        fetchNewRecipes()

        // 검색어 변경 시 debounce 적용하여 필터링
        uiState
            .map { it.searchText }
            .debounce(DEBOUNCE_TIMEOUT_MILLIS)
            .distinctUntilChanged()
            .onEach { fetchRecipes() }
            .launchIn(viewModelScope)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnCategoryClick -> {
                changeCategory(action.category)
            }

            is HomeAction.OnSearchClick -> {
                searchClick()
            }

            is HomeAction.OnSearchQueryChange -> {
                changeSearchText(action.query)
            }

            is HomeAction.OnRecipeClick -> {
                recipeClick(action.recipeId)
            }

            is HomeAction.OnBookmarkClick -> {
                viewModelScope.launch {
                    toggleBookmarkUseCase(action.recipe)
                }
            }
        }
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


    private fun fetchNewRecipes() {
        viewModelScope.launch {
            setNewRecipeLoading(true)
            try {
                val recipes = recipeRepository.getRecipes().take(2)
                changeNewRecipes(recipes)
            } finally {
                setNewRecipeLoading(false)
            }
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
        //_uiState.update {
        //    it.copy(searchText = searchText)
        //}

        // Flow에서 debounce 처리하므로 여기서는 상태만 업데이트
    }

    fun searchClick() {
        // 검색 버튼 클릭 시 SearchRecipesRoot 페이지로 이동
        viewModelScope.launch {
            _eventFlow.emit(HomeEvent.NavigateToSearch)
        }
    }

    fun recipeClick(recipeId: Long) {
        viewModelScope.launch {
            _eventFlow.emit(HomeEvent.NavigateToRecipeDetails(recipeId))
        }
    }

    private fun changeNewRecipes(recipes: List<Recipe>) {
        _uiState.update { it.copy(newRecipes = recipes) }
    }

    private fun setNewRecipeLoading(isLoading: Boolean) {
        _uiState.update { it.copy(isNewRecipesLoading = isLoading) }
    }


    companion object {
        const val DEBOUNCE_TIMEOUT_MILLIS = 500L
    }
}


sealed interface HomeEvent {
    data object NavigateToSearch : HomeEvent
    data class NavigateToRecipeDetails(val recipeId: Long) : HomeEvent
}