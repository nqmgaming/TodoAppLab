package com.nqmgaming.todoapplab.domain.model

import com.nqmgaming.todoapplab.core.common.Priority
import java.util.Date

data class Todo(
    val id: Long,
    val title: String,
    val description: String? = null,
    val createdDate: Date = Date(),
    val dueDate: Date? = null,
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.LOW,
)
