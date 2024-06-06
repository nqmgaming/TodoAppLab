package com.nqmgaming.todoapplab.presentation.add_todo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.nqmgaming.todoapplab.core.common.Priority
import com.nqmgaming.todoapplab.domain.model.Todo
import com.nqmgaming.todoapplab.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddTodoScreenViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
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
                val title = state.title
                val description = state.description
                val priority = state.priority
                val dueDate = state.dueDate

                if (title.isBlank()) {
                    _state.value = state.copy(

                    )
                    return
                }

                val todo = Todo(
                    title = title,
                    description = description,
                    priority = priority,
                    dueDate = dueDate
                )

                todoRepository.insertTodo(todo)
                _state.value = state.copy(
                    title = "",
                    description = "",
                    priority = Priority.LOW,
                    dueDate = Date(),
                )
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