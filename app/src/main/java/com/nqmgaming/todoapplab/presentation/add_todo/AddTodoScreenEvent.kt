package com.nqmgaming.todoapplab.presentation.add_todo

import com.nqmgaming.todoapplab.core.common.Priority
import java.util.Date

sealed class AddTodoScreenEvent {
    data class TitleChanged(val title: String) : AddTodoScreenEvent()
    data class DescriptionChanged(val description: String) : AddTodoScreenEvent()
    data class PriorityChanged(val priority: Priority) : AddTodoScreenEvent()
    data class DueDateChanged(val dueDate: Date) : AddTodoScreenEvent()
    data object SaveButtonClicked : AddTodoScreenEvent()
    data object BackButtonClicked : AddTodoScreenEvent()
    data object TodoAdded : AddTodoScreenEvent()
}