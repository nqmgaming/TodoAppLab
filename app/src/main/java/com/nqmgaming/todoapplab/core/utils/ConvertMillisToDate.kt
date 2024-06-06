package com.nqmgaming.todoapplab.core.utils

import java.text.SimpleDateFormat
import java.util.Date

object ConvertMillisToDate{
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(millis))
    }

}