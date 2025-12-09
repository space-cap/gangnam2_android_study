package com.survivalcoding.gangnam2kiandroidstudy.presentation.todo

class TodoDataSourceImpl : TodoDataSource {
    override suspend fun getTodos(): List<Todo> {
        return listOf(
            Todo(1, "Todo 1", "Description 1", false),
            Todo(2, "Todo 2", "Description 2", true),
        )
    }
}
