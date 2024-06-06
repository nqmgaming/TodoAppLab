package com.nqmgaming.todoapplab.presentation.edit_todo

import com.nqmgaming.todoapplab.core.common.Priority
import java.util.Date

sealed class EditTodoScreenEvent {
    data class TitleChanged(val title: String) : EditTodoScreenEvent()
    data class DescriptionChanged(val description: String) : EditTodoScreenEvent()
    data class PriorityChanged(val priority: Priority) : EditTodoScreenEvent()
    data class DueDateChanged(val dueDate: Date) : EditTodoScreenEvent()
    data object SaveButtonClicked : EditTodoScreenEvent()
    data object TodoUpdated : EditTodoScreenEvent()
}