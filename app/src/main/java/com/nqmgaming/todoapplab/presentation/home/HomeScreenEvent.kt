package com.nqmgaming.todoapplab.presentation.home

sealed class HomeScreenEvent {
    data class TodoSelected(val todoId: Long) : HomeScreenEvent()
    object AddTodoClicked : HomeScreenEvent()
    data class TodoCheckedChanged(val todoId: Long, val isChecked: Boolean) : HomeScreenEvent()
    data class SearchQueryChanged(val query: String) : HomeScreenEvent()
}