package com.example.dailytimer.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StopwatchViewModel : ViewModel() {
    val timeText = mutableStateOf("00:00:00")
    val msText = mutableStateOf(".00")
    val isRunning = mutableStateOf(false)
    val laps = mutableStateListOf<Pair<String, String>>()
    private var elapsedMs = 0L

    fun start() {
        if (isRunning.value) return
        isRunning.value = true
        viewModelScope.launch {
            while (isRunning.value) {
                delay(10)
                elapsedMs += 10
                val totalSec = elapsedMs / 1000
                val h = totalSec / 3600
                val m = (totalSec % 3600) / 60
                val s = totalSec % 60
                timeText.value = "%02d:%02d:%02d".format(h, m, s)
                msText.value = ".%02d".format((elapsedMs % 1000) / 10)
            }
        }
    }

    fun pause() {
        isRunning.value = false
    }

    fun reset() {
        isRunning.value = false
        elapsedMs = 0L
        timeText.value = "00:00:00"
        msText.value = ".00"
        laps.clear()
    }

    fun lap() {
        laps.add(0, "计次 ${laps.size + 1}" to (timeText.value + msText.value))
    }

    fun clearLaps() {
        laps.clear()
    }
}
