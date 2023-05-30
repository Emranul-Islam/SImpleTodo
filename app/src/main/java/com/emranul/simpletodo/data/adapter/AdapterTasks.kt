package com.emranul.simpletodo.data.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.DifferCallback
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emranul.simpletodo.R
import com.emranul.simpletodo.data.model.TaskData
import com.emranul.simpletodo.databinding.ItemTaskBinding
import javax.inject.Inject

class AdapterTasks @Inject constructor() :
    PagingDataAdapter<TaskData, AdapterTasks.TasksViewHolder>(MyDiffer) {

    class TasksViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    private object MyDiffer : DiffUtil.ItemCallback<TaskData>() {
        override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.binding.apply {
                taskTitle.text = item.title
                taskRadioBtn.isChecked = item.isCompleted
                if (item.isCompleted) {
                    taskTitle.changeColor(R.color.text_task_complete)
                    taskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    taskTitle.changeColor(R.color.text)
                    taskTitle.paintFlags =taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }

            holder.itemView.setOnClickListener {
                onClick?.invoke(position,item)
            }

            holder.itemView.setOnLongClickListener {
                onClickRemove?.invoke(position,item)
                true
            }
        }
    }


    var onClick: ((Int,TaskData) -> Unit)? = null
    var onClickRemove: ((Int,TaskData) -> Unit)? = null
}

/**
 * An extension function for changing text color ;)
 * */
fun TextView.changeColor(color: Int) =
    setTextColor(ColorStateList.valueOf(this.context.getColor(color)))