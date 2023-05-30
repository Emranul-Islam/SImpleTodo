package com.emranul.simpletodo.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity("task_table")
data class TaskData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id:Int ,
    @ColumnInfo("title")
    val title:String,
    @ColumnInfo("ic_completed")
    var isCompleted:Boolean,
    @ColumnInfo("timestamp")
    var timestamp:Long= System.currentTimeMillis()
)
