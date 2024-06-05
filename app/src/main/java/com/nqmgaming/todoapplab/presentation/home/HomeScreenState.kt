package com.nqmgaming.todoapplab.presentation.home

import com.nqmgaming.todoapplab.domain.model.Todo

data class HomeScreenState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false
)