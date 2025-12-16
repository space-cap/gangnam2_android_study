package com.survivalcoding.gangnam2kiandroidstudy

import android.app.Application
import com.survivalcoding.gangnam2kiandroidstudy.data.datasource.RecipeDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.datasource.RecipeDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.data.repository.RecipeRepositoryImpl
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.todo.TodoDataSource
import com.survivalcoding.gangnam2kiandroidstudy.presentation.todo.TodoDataSourceImpl
import com.survivalcoding.gangnam2kiandroidstudy.presentation.todo.TodoRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.todo.TodoRepositoryImpl

class AppApplication : Application() {

    private val todoDataSource: TodoDataSource by lazy {
        TodoDataSourceImpl()
    }

    val todoRepository: TodoRepository by lazy {
        TodoRepositoryImpl(todoDataSource)
    }

    private val recipeDataSource: RecipeDataSource by lazy {
        RecipeDataSourceImpl(this)
    }

    val recipeRepository: RecipeRepository by lazy {
        RecipeRepositoryImpl(recipeDataSource)
    }
}
