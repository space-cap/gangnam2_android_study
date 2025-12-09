package com.survivalcoding.gangnam2kiandroidstudy.presentation.todo

data class Todo(
    val id: Long,
    val title: String,
    val description: String? = null,
    val isComplete: Boolean = false
)


interface TodoDataSource {
    suspend fun getTodos(): List<Todo>
}