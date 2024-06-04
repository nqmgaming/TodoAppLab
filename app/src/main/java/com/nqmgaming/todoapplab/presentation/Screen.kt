package com.nqmgaming.todoapplab.presentation

sealed class Screen(val route: String) {
    data object HomeScreen: Screen("home_screen")
    data object AddTodoScreen: Screen("add_todo_screen")
    data object EditTodoScreen: Screen("edit_todo_screen")
}