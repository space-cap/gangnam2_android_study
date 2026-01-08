package com.survivalcoding.gangnam2kiandroidstudy.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.survivalcoding.gangnam2kiandroidstudy.data.database.AppDatabase
import com.survivalcoding.gangnam2kiandroidstudy.data.datasource.RecipeDataSourceImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class RecipeRepositoryImplTest {
    @Test
    fun getRecipes() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dataSource = RecipeDataSourceImpl(
            context = context
        )

        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        val bookmarkRepository = BookmarkRepositoryImpl(db.bookmarkDao())

        val repository = RecipeRepositoryImpl(dataSource, bookmarkRepository)

        // when
        val recipes = repository.getRecipes()

        // then
        assertTrue("불러온 레시피 리스트가 비어있습니다.", recipes.isNotEmpty())
        println(recipes[0].toString())

    }

}
