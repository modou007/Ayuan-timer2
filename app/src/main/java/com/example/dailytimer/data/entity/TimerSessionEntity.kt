package com.example.dailytimer.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "timer_sessions",
    foreignKeys = [
        ForeignKey(
            entity = TimerEntity::class,
            parentColumns = ["id"],
            childColumns = ["timerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimerSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timerId: Long,
    val startTime: Long,
    val endTime: Long? = null,
    val durationMs: Long = 0
)
