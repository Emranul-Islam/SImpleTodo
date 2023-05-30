package com.emranul.simpletodo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.emranul.simpletodo.data.TaskRepository
import com.emranul.simpletodo.data.model.TaskData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    fun addTask(task: TaskData) = viewModelScope.launch(Dispatchers.IO) {
        repository.addTask(task)
    }

    fun updateTask(task: TaskData) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTask(task)
    }
    fun removeTask(task: TaskData) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeTask(task)
    }

    fun allTask() = repository.allTaskData().cachedIn(viewModelScope)

}