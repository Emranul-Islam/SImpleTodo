package com.emranul.simpletodo.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.emranul.simpletodo.data.model.TaskData
import com.emranul.simpletodo.data.room.TaskDao
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {


    suspend fun addTask(taskData: TaskData) = taskDao.addTask(taskData)

    suspend fun updateTask(taskData: TaskData) = taskDao.updateTask(taskData)

    suspend fun removeTask(taskData: TaskData) = taskDao.removeTask(taskData)

    fun allTaskData() = Pager(
        PagingConfig(pageSize = 10)
    ) {
        taskDao.allTask()
    }.liveData

}