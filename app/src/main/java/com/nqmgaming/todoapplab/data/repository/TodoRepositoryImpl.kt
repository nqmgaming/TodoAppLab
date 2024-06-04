package com.nqmgaming.todoapplab.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.nqmgaming.todoapplab.core.utils.Resources
import com.nqmgaming.todoapplab.data.local.TodoDao
import com.nqmgaming.todoapplab.data.local.TodoDatabase
import com.nqmgaming.todoapplab.data.mapper.toDomain
import com.nqmgaming.todoapplab.data.mapper.toDto
import com.nqmgaming.todoapplab.domain.model.Todo
import com.nqmgaming.todoapplab.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dao: TodoDao,
    private val database: TodoDatabase,
) : TodoRepository {

    private val TAG = "TodoRepositoryImpl"

    override fun getAllTodos(): Flow<Resources<List<Todo>>> {
        return flow {
            emit(Resources.Loading(isLoading = true))
            val todos = try {
                dao.getAllTodos()
            } catch (e: Exception) {
                Log.e(TAG, "getAllTodos: ${e.stackTraceToString()}")
                emit(Resources.Error(message = e.message ?: "An error occurred"))
                return@flow
            }

            if (todos != null) {
                emit(Resources.Loading(isLoading = false))
            }

            todos.let { flow ->
                flow.map { listing ->
                    Resources.Success(
                        data = listing.map { it.toDomain() }
                    )
                }.let { emitAll(it) }
                emit(Resources.Loading(isLoading = false))
            }

        }
    }

    override fun getTodoById(id: Long): Flow<Resources<Todo>> {
        return flow {
            emit(Resources.Loading(isLoading = true))
            val todo = try {
                dao.getTodoById(id)
            } catch (e: Exception) {
                Log.e(TAG, "getTodoById: ${e.stackTraceToString()}")
                emit(Resources.Error(message = e.message ?: "An error occurred"))
                return@flow
            }

            if (todo != null) {
                emit(Resources.Loading(isLoading = false))
            }

            todo.let { flow ->
                flow.map { listing ->
                    Resources.Success(
                        data = listing.toDomain()
                    )
                }.let { emitAll(it) }
                emit(Resources.Loading(isLoading = false))
            }

        }
    }


    override suspend fun insertTodo(todo: Todo) {
        database.withTransaction {
            dao.insertTodo(todo.toDto())
        }
    }

    override suspend fun updateTodo(todo: Todo) {
        database.withTransaction {
            dao.updateTodo(todo.toDto())
        }
    }

    override suspend fun deleteTodoById(todo: Todo) {
        database.withTransaction {
            dao.deleteTodo(todo.toDto())
        }
    }

}