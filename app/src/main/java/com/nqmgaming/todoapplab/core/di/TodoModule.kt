package com.nqmgaming.todoapplab.core.di

import android.content.Context
import androidx.room.Room
import com.nqmgaming.todoapplab.data.local.MigrationDb.MIGRATION_1_2
import com.nqmgaming.todoapplab.data.local.TodoDao
import com.nqmgaming.todoapplab.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Provides
    fun providesRoomDao(database: TodoDatabase): TodoDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).addMigrations(
            MIGRATION_1_2
        ).build()
    }
}