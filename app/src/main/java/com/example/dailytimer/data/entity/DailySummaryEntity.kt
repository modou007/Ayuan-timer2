package com.example.dailytimer.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_summaries",
    foreignKeys = [
        ForeignKey(
            entity = TimerEntity::class,
            parentColumns = ["id"],
            childColumns = ["timerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DailySummaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // yyyy-MM-dd
    val timerId: Long,
    val totalDurationMs: Long = 0
)
