package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    suspend fun toggleBookmark(recipeId: Long): Boolean

    suspend fun addBookmark(recipeId: Long)

    suspend fun removeBookmark(recipeId: Long)

    suspend fun isBookmarked(recipeId: Long): Boolean

    fun getBookmarkedRecipeIds(): Flow<List<Long>>
}
