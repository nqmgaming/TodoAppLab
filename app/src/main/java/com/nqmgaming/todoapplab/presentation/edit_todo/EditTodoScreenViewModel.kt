package com.nqmgaming.todoapplab.presentation.edit_todo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqmgaming.todoapplab.domain.model.Todo
import com.nqmgaming.todoapplab.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
@HiltViewModel
class EditTodoScreenViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val todoId: Long = savedStateHandle.get<Long>("todoId") ?: -1
    private val _state = mutableStateOf(EditTodoScreenState())
    val state get() = _state.value

    init {
        if (todoId != -1L) {
            viewModelScope.launch {
                val todo = todoRepository.getTodoById(todoId)
                todo.collect { resource ->
                    if (resource.data != null) {
                        _state.value = state.copy(
                            title = resource.data.title,
                            description = resource.data.description ?: "",
                            priority = resource.data.priority,
                            dueDate = resource.data.dueDate ?: Date(),
                        )
                    } else {
                        // handle error
                    }
                }
            }
        }
    }

    suspend fun onEvent(event: EditTodoScreenEvent) {
        when (event) {
            is EditTodoScreenEvent.TitleChanged -> {
                _state.value = state.copy(
                    title = event.title,
                    isSaveButtonEnabled = event.title.isNotBlank()
                )
            }

            is EditTodoScreenEvent.DescriptionChanged -> {
                _state.value = state.copy(
                    description = event.description
                )
            }

            is EditTodoScreenEvent.PriorityChanged -> {
                _state.value = state.copy(
                    priority = event.priority
                )
            }

            is EditTodoScreenEvent.DueDateChanged -> {
                _state.value = state.copy(
                    dueDate = event.dueDate
                )
            }

            is EditTodoScreenEvent.SaveButtonClicked -> {
                val title = state.title
                val description = state.description
                val priority = state.priority
                val dueDate = state.dueDate

                if (title.isBlank()) {
                    _state.value = state.copy(
                        isSaveButtonEnabled = false
                    )
                    return
                }

                if (todoId != -1L) {
                    todoRepository.updateTodo(
                        Todo(
                            id = todoId,
                            title = title,
                            description = description,
                            priority = priority,
                            dueDate = dueDate
                        )
                    )
                }
            }

            EditTodoScreenEvent.TodoUpdated -> TODO()
        }
    }
}