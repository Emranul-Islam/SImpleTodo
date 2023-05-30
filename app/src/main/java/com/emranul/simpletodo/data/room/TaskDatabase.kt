package com.emranul.simpletodo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emranul.simpletodo.data.model.TaskData

@Database([TaskData::class], version = 1)
abstract class TaskDatabase:RoomDatabase() {
    abstract fun taskDao():TaskDao
}