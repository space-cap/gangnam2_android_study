package com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipes

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.Recipe

/**
 * 저장된 레시피 화면의 UI 상태를 나타내는 데이터 클래스입니다.
 *
 * @property recipes 저장된 레시피 목록
 * @property isLoading 데이터 로딩 중인지 여부
 */
data class SavedRecipesState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
)
