package com.nqmgaming.todoapplab.core.di

import android.content.Context
import androidx.room.Room
import com.nqmgaming.todoapplab.data.local.TodoDao
import com.nqmgaming.todoapplab.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
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
        ).fallbackToDestructiveMigration().build()
    }
}