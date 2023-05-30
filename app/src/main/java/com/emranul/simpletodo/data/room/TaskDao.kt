package com.emranul.simpletodo.data.room

import androidx.paging.PagingSource
import androidx.room.*
import com.emranul.simpletodo.data.model.TaskData

@Dao
interface TaskDao {

    @Insert( onConflict = OnConflictStrategy.NONE)
    suspend fun addTask(task:TaskData)

    @Update()
    suspend fun updateTask(task: TaskData)

    @Delete()
    suspend fun removeTask(task: TaskData)

    @Query("SELECT * FROM task_table ORDER BY CASE WHEN ic_completed = 1 THEN 1 ELSE 0 END, timestamp DESC")
    fun allTask():PagingSource<Int,TaskData>


}