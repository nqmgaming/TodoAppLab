package com.nqmgaming.todoapplab.data.mapper

import com.nqmgaming.todoapplab.data.local.dto.TodoDto
import com.nqmgaming.todoapplab.domain.model.Todo

fun TodoDto.toDomain(): Todo {
    return Todo(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        priority = this.priority,
        createdDate = this.createdDate,
        dueDate = this.dueDate,
    )
}

fun Todo.toDto(): TodoDto {
    return TodoDto(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        priority = this.priority,
        createdDate = this.createdDate,
        dueDate = this.dueDate,
    )
}