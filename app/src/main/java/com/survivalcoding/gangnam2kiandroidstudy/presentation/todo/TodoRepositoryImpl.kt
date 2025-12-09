package com.survivalcoding.gangnam2kiandroidstudy.presentation.todo

class TodoRepositoryImpl(private val dataSource: TodoDataSource) : TodoRepository {
    override suspend fun getTodos(): List<Todo> {
        return dataSource.getTodos()
    }
}
