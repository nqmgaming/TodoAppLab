package com.nqmgaming.todoapplab.data.repository

import com.nqmgaming.todoapplab.data.local.TodoDao
import com.nqmgaming.todoapplab.data.local.TodoDatabase
import com.nqmgaming.todoapplab.domain.model.Todo
import com.nqmgaming.todoapplab.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dao: TodoDao,
    private val database: TodoDatabase
) : TodoRepository {
    override suspend fun getAllTodos() {
        TODO("Not yet implemented")
    }

    override suspend fun getTodoById(id: Long): Todo {
        TODO("Not yet implemented")
    }

    override suspend fun insertTodo() {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodo() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodo() {
        TODO("Not yet implemented")
    }
}