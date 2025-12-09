package com.survivalcoding.gangnam2kiandroidstudy.presentation.todo

interface TodoRepository {
    suspend fun getTodos(): List<Todo>
}
