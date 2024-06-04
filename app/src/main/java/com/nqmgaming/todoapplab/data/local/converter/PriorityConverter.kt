package com.nqmgaming.todoapplab.data.local.converter

import androidx.room.TypeConverter
import com.nqmgaming.todoapplab.core.common.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}