package com.emranul.simpletodo.di

import android.content.Context
import androidx.room.Room
import com.emranul.simpletodo.data.room.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun roomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, TaskDatabase::class.java, "task_database.db").build()

    @Provides
    @Singleton
    fun roomDao(database: TaskDatabase) =database.taskDao()

}