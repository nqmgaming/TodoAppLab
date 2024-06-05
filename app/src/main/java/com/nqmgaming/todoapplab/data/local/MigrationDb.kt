package com.nqmgaming.todoapplab.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object MigrationDb {
//    val MIGRATION_1_2 = object : Migration(1, 2) {
//        override fun migrate(db: SupportSQLiteDatabase) {
//            db.execSQL("CREATE TABLE `new_todos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `createdDate` INTEGER NOT NULL, `dueDate` INTEGER, `isCompleted` INTEGER NOT NULL, `priority` INTEGER NOT NULL)")
//            db.execSQL("INSERT INTO `new_todos` (`id`, `title`, `description`, `createdDate`, `dueDate`, `isCompleted`, `priority`) SELECT `id`, `title`, `description`, `createdDate`, `dueDate`, `isCompleted`, `priority` FROM `todos`")
//            db.execSQL("DROP TABLE `todos`")
//            db.execSQL("ALTER TABLE `new_todos` RENAME TO `todos`")
//        }
//    }
}