package com.nqmgaming.todoapplab.presentation.edit_todo

sealed class EditScreenEvent {
    data class TitleChanged(val title: String) : EditScreenEvent()
    data class DescriptionChanged(val description: String) : EditScreenEvent()
    data class PriorityChanged(val priority: String) : EditScreenEvent()
    data class DueDateChanged(val dueDate: String) : EditScreenEvent()
    data object SaveButtonClicked : EditScreenEvent()
    data object TodoUpdated : EditScreenEvent()
}