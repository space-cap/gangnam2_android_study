package com.survivalcoding.gangnam2kiandroidstudy.domain.usercase

import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository

class ToggleBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository,
) {
    suspend operator fun invoke(recipeId: Long) {
        bookmarkRepository.toggleBookmark(recipeId)
    }
}