package com.survivalcoding.gangnam2kiandroidstudy

import android.app.Application
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
}
