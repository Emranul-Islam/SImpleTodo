package com.emranul.simpletodo.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.emranul.simpletodo.R
import com.emranul.simpletodo.data.adapter.AdapterTasks
import com.emranul.simpletodo.data.model.TaskData
import com.emranul.simpletodo.databinding.ActivityMainBinding
import com.emranul.simpletodo.databinding.PopupAddTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var adapterTasks: AdapterTasks

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyclerTask()
        handleResponse()

        binding.btnAddTask.setOnClickListener {
            showTaskPopup()
        }

        binding.imageDashboard.setOnClickListener {
            toasty("Don't have any Action on it ;)")
        }

    }

    private fun showTaskPopup() {
        val popupBinding = PopupAddTaskBinding.inflate(layoutInflater)
        val dialog = Dialog(this).apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(popupBinding.root)
            show()
        }

        popupBinding.btnAdd.setOnClickListener {
            val title = popupBinding.titleEt.text.toString()
            if (title.isEmpty()) {
                popupBinding.titleLayout.error = "Please write first"
            } else {
                val taskData = TaskData(0, title, false)
                viewModel.addTask(taskData)
                toasty("Added")
                dialog.dismiss()
            }
        }


    }

    private fun handleResponse() {

        viewModel.allTask().observe(this) {
            lifecycleScope.launch {
                adapterTasks.submitData(it)
            }
        }

        lifecycleScope.launch {
            adapterTasks.loadStateFlow.collect {
                if (it.source.refresh is LoadState.Loading) {
                    binding.progressBar.isVisible = true
                } else {
                    binding.progressBar.isVisible = false
                    binding.textStatus.isVisible = adapterTasks.snapshot().isEmpty()
                }

            }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun recyclerTask() {
        binding.recyclerTasks.apply {
            adapter = adapterTasks
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        adapterTasks.onClick = { position, it ->
            it.isCompleted = !it.isCompleted
            viewModel.updateTask(it)
            adapterTasks.notifyDataSetChanged()
            toasty("updated")
        }


        adapterTasks.onClickRemove = { position, it ->

            MaterialAlertDialogBuilder(this).apply {
                setTitle("Do you want to Remove ?")
                setMessage("Your task '${it.title}' wille remove permanently.")
                setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                    viewModel.removeTask(it)

                    toasty("removed")
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }
}

fun Context.toasty(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()