package com.nqmgaming.todoapplab.presentation.home

import com.nqmgaming.todoapplab.domain.model.Todo

sealed class HomeScreenEvent {
    data class TodoSelected(val todoId: Long) : HomeScreenEvent()
    data object AddTodoClicked : HomeScreenEvent()
    data class TodoCheckedChanged(val todo: Todo, val isChecked: Boolean) : HomeScreenEvent()
    data class SearchQueryChanged(val query: String) : HomeScreenEvent()
    data class DeleteTodoClicked(val todo: Todo) : HomeScreenEvent()
}