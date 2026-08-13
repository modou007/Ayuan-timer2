package com.example.dailytimer.viewmodel

import androidx.compose.ui.graphics.Color
import com.example.dailytimer.ui.theme.*

enum class TimerStatus { IDLE, RUNNING, PAUSED }

data class TimerState(
    val id: Long = 0,
    val name: String = "",
    val iconType: String = "ball",
    val iconColor: String = "red",
    val status: TimerStatus = TimerStatus.IDLE,
    val dailyTotal: String = "0h 00m",
    val sessionTime: String = "00:00:00",
    val progress: Float = 0f,
    val color: Color = Red
)

fun String.toTimerColor(): Color = when (this) {
    "red" -> Red
    "yellow" -> Yellow
    "orange" -> Orange
    "green" -> Green
    "blue" -> Blue
    "purple" -> Purple
    else -> Red
}
