package com.example.dailytimer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timers")
data class TimerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val iconColor: String, // "red","yellow","green","blue","purple"
    val iconType: String,  // "ball","face","cat","emoji","animal"
    val createdAt: Long = System.currentTimeMillis()
)
