package com.nqmgaming.todoapplab.presentation.edit_todo

import com.nqmgaming.todoapplab.core.common.Priority
import java.util.Date

data class EditTodoScreenState (
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val dueDate: Date = Date(),
    val isSaveButtonEnabled: Boolean = false
)