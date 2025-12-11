package com.survivalcoding.gangnam2kiandroidstudy.presentation.todo

class TodoDataSourceImpl : TodoDataSource {

    // 간단한 데모용 인메모리 리스트
    private val todos = mutableListOf(
        Todo(1, "Todo 1", "Description 1", false),
        Todo(2, "Todo 2", "Description 2", true),
    )

    override suspend fun getTodos(): List<Todo> {
        // 외부에서 변경되지 않도록 복사본 반환
        return todos.toList()
    }

    override suspend fun addTodo(todo: Todo) {
        todos.add(todo)
    }
}
