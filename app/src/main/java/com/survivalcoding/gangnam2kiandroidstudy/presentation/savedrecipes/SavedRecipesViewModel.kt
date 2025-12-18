package com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.usercase.GetSavedRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 저장된 레시피 화면의 ViewModel입니다.
 *
 * @property getSavedRecipesUseCase 저장된 레시피를 가져오는 UseCase
 */
@HiltViewModel
class SavedRecipesViewModel @Inject constructor(
    private val getSavedRecipesUseCase: GetSavedRecipesUseCase,
) : ViewModel() {

    // UI 상태를 private MutableStateFlow로 관리합니다.
    private val _uiState = MutableStateFlow(SavedRecipesState())

    // 외부에는 읽기 전용 StateFlow로 UI 상태를 노출합니다.
    val uiState = _uiState.asStateFlow()

    init {
        // ViewModel이 생성될 때 레시PI 목록을 가져옵니다.
        viewModelScope.launch {
            try {
                // 로딩 상태를 true로 설정합니다.
                _uiState.value = _uiState.value.copy(isLoading = true)
                // 리포지토리에서 레시피 목록을 가져옵니다.
                val recipes = getSavedRecipesUseCase.invoke()
                // 가져온 레시피 목록으로 UI 상태를 업데이트하고 로딩 상태를 false로 설정합니다.
                _uiState.value = _uiState.value.copy(recipes = recipes, isLoading = false)
            } catch (e: Exception) {
                // 에러 발생 시 로딩을 중지하고 에러 상태를 설정합니다.
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}
