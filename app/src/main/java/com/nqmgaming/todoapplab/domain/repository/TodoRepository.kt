package com.nqmgaming.todoapplab.domain.repository

import com.nqmgaming.todoapplab.core.utils.Resources
import com.nqmgaming.todoapplab.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getAllTodos(): Flow<Resources<List<Todo>>>
    fun getTodoById(id: Long): Flow<Resources<Todo>>
    suspend fun insertTodo(todo: Todo)
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodoById(todo: Todo)
}