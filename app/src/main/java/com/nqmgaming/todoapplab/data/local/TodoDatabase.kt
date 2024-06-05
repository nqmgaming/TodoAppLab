package com.nqmgaming.todoapplab.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nqmgaming.todoapplab.data.local.converter.DateConverter
import com.nqmgaming.todoapplab.data.local.converter.PriorityConverter
import com.nqmgaming.todoapplab.data.local.dto.TodoDto

@Database(entities = [TodoDto::class], version = 2)
@TypeConverters(PriorityConverter::class, DateConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract val dao: TodoDao

    companion object {
        const val DATABASE_NAME = "todo_database"
    }
}