package com.nqmgaming.todoapplab.domain.repository

import com.nqmgaming.todoapplab.domain.model.Todo

interface TodoRepository {
    suspend fun getAllTodos()
    suspend fun getTodoById(id: Long): Todo
    suspend fun insertTodo()
    suspend fun updateTodo()
    suspend fun deleteTodo()
}