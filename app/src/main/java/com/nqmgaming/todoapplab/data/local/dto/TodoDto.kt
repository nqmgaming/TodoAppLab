package com.nqmgaming.todoapplab.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nqmgaming.todoapplab.core.common.Priority
import java.util.Date

@Entity(tableName = "todos")
data class TodoDto(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val title: String,
    val description: String? = null,
    val createdDate: Date = Date(),
    val dueDate: Date? = null,
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.LOW,
)