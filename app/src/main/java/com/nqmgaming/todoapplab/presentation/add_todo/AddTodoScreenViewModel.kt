package com.nqmgaming.todoapplab.presentation.add_todo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nqmgaming.todoapplab.domain.model.Todo
import com.nqmgaming.todoapplab.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTodoScreenViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _state = mutableStateOf(AddTodoScreenState())
    val state get() = _state.value

    suspend fun onEvent(event: AddTodoScreenEvent) {
        when (event) {
            is AddTodoScreenEvent.TitleChanged -> {
                _state.value = state.copy(
                    title = event.title,
                )
            }

            is AddTodoScreenEvent.DescriptionChanged -> {
                _state.value = state.copy(
                    description = event.description,
                )
            }

            is AddTodoScreenEvent.PriorityChanged -> {
                _state.value = state.copy(
                    priority = event.priority
                )
            }

            is AddTodoScreenEvent.DueDateChanged -> {
                _state.value = state.copy(
                    dueDate = event.dueDate,
                )
            }

            AddTodoScreenEvent.SaveButtonClicked -> {
                if (state.title.isBlank() || state.description.isBlank()) {
                    _state.value = state.copy(
                        isSaveButtonEnabled = false
                    )
                } else {
                    // format date

                    todoRepository.insertTodo(
                        Todo(
                            title = state.title,
                            description = state.description,
                            priority = state.priority,
                            dueDate = state.dueDate,
                            id = 0
                        )
                    )
                    _state.value = AddTodoScreenState()
                }
            }

            AddTodoScreenEvent.BackButtonClicked -> {
                _state.value = AddTodoScreenState()
            }

            AddTodoScreenEvent.TodoAdded -> {
                _state.value = AddTodoScreenState()
            }
        }
    }


}