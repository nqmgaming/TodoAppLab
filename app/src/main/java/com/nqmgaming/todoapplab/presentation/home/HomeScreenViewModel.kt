package com.nqmgaming.todoapplab.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqmgaming.todoapplab.core.utils.Resources
import com.nqmgaming.todoapplab.domain.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state get() = _state.value
    private var getTodoItemsJob: Job? = null

    suspend fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.TodoSelected -> {
                // navigate to detail screen
            }

            is HomeScreenEvent.AddTodoClicked -> {
                // navigate to add todo screen
            }

            is HomeScreenEvent.TodoCheckedChanged -> {
//                todoRepository.updateTodo()
            }

            is HomeScreenEvent.SearchQueryChanged -> {
                // search query
            }
        }
    }

    fun getTodos() {
        getTodoItemsJob?.cancel()
        getTodoItemsJob = viewModelScope.launch {
            _state.value = state.copy(isLoading = true)
            todoRepository.getAllTodos().collect { resource ->
                when (resource) {
                    is Resources.Success -> {
                        _state.value = resource.data?.let {
                            state.copy(
                                todos = it,
                                isLoading = false
                            )
                        }!!
                    }

                    is Resources.Error -> {
                        _state.value = state.copy(isLoading = false)
                    }

                    is Resources.Loading -> {
                        _state.value = state.copy(isLoading = true)
                    }
                }
            }
        }
    }

}