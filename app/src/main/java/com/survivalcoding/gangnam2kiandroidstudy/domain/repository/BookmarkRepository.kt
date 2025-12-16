package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

interface BookmarkRepository {
    suspend fun toggleBookmark(recipeId: Long): Boolean
}

