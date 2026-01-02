package com.survivalcoding.gangnam2kiandroidstudy.data.repository

import android.util.Log
import com.survivalcoding.gangnam2kiandroidstudy.data.dao.BookmarkDao
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow


class BookmarkRepositoryImpl(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {
    override suspend fun toggleBookmark(recipeId: Long): Boolean {
        Log.d("BookmarkRepository", "Toggled bookmark for recipeId: $recipeId")
        val isCurrentlyBookmarked = isBookmarked(recipeId)
        if (isCurrentlyBookmarked) {
            removeBookmark(recipeId)
        } else {
            addBookmark(recipeId)
        }
        return !isCurrentlyBookmarked
    }

    override suspend fun addBookmark(recipeId: Long) {
        bookmarkDao.insert(recipeId)
        Log.d("BookmarkRepository", "Added bookmark for recipeId: $recipeId")
    }

    override suspend fun removeBookmark(recipeId: Long) {
        bookmarkDao.delete(recipeId)
        Log.d("BookmarkRepository", "Removed bookmark for recipeId: $recipeId")
    }

    override suspend fun isBookmarked(recipeId: Long): Boolean {
        return bookmarkDao.isBookmarked(recipeId)
    }

    override fun getBookmarkedRecipeIds(): Flow<List<Long>> {
        return bookmarkDao.getIds()
    }
}
