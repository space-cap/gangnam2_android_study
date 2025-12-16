package com.survivalcoding.gangnam2kiandroidstudy.data.repository

import android.util.Log
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository

class BookmarkRepositoryImpl : BookmarkRepository {
    override suspend fun toggleBookmark(recipeId: Long): Boolean {
        Log.d("BookmarkRepository", "Toggled bookmark for recipeId: $recipeId")
        return true
    }
}
