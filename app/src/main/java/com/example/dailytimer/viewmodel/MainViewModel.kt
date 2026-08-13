package com.example.dailytimer.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytimer.data.dao.DailySummaryDao
import com.example.dailytimer.data.dao.TimerDao
import com.example.dailytimer.data.dao.TimerSessionDao
import com.example.dailytimer.data.entity.DailySummaryEntity
import com.example.dailytimer.data.entity.TimerEntity
import com.example.dailytimer.data.entity.TimerSessionEntity
import com.example.dailytimer.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val timerDao: TimerDao,
    private val sessionDao: TimerSessionDao,
    private val summaryDao: DailySummaryDao
) : ViewModel() {

    val timers = mutableStateListOf<TimerState>()

    val showCreateDialog = mutableStateOf(false)
    val showDeleteDialog = mutableStateOf(false)
    val showResetSheet = mutableStateOf(false)
    val deleteTarget = mutableStateOf<TimerState?>(null)

    init {
        seedIfEmpty()
        observeTimers()
        startTicker()
    }

    private fun seedIfEmpty() {
        viewModelScope.launch {
            val existing = timerDao.getAll()
            existing.collectLatest { list ->
                if (list.isEmpty()) {
                    // Seed initial data
                    timerDao.insert(TimerEntity(name = "午睡计时", iconColor = "red", iconType = "ball"))
                    timerDao.insert(TimerEntity(name = "夜间睡眠", iconColor = "yellow", iconType = "face"))
                    timerDao.insert(TimerEntity(name = "喂奶间隔", iconColor = "orange", iconType = "cat"))
                }
            }
        }
    }

    private fun observeTimers() {
        viewModelScope.launch {
            timerDao.getAll().collectLatest { entities ->
                timers.clear()
                entities.forEach { entity ->
                    val todayTotal = getTodayTotal(entity.id)
                    timers.add(
                        TimerState(
                            id = entity.id,
                            name = entity.name,
                            iconType = entity.iconType,
                            iconColor = entity.iconColor,
                            status = TimerStatus.IDLE,
                            dailyTotal = todayTotal,
                            sessionTime = "00:00:00",
                            progress = 0f,
                            color = entity.iconColor.toTimerColor()
                        )
                    )
                }
            }
        }
    }

    private fun getTodayTotal(timerId: Long): String {
        // Simplified: return placeholder until DB query completes
        return "0h 00m"
    }

    private fun startTicker() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                timers.forEachIndexed { index, t ->
                    if (t.status == TimerStatus.RUNNING) {
                        val parts = t.sessionTime.split(":").map { it.toInt() }
                        var h = parts.getOrElse(0) { 0 }
                        var m = parts.getOrElse(1) { 0 }
                        var s = parts.getOrElse(2) { 0 } + 1
                        if (s >= 60) { s = 0; m++ }
                        if (m >= 60) { m = 0; h++ }
                        timers[index] = t.copy(sessionTime = "%02d:%02d:%02d".format(h, m, s))
                    }
                }
            }
        }
    }

    fun addTimer(name: String, iconType: String, iconColor: String) {
        viewModelScope.launch {
            timerDao.insert(TimerEntity(
                name = name,
                iconColor = iconColor,
                iconType = iconType
            ))
        }
    }

    fun deleteTimer(id: Long) {
        viewModelScope.launch {
            timerDao.deleteById(id)
            timers.removeAll { it.id == id }
        }
    }

    fun startTimer(id: Long) {
        val index = timers.indexOfFirst { it.id == id }
        if (index >= 0) {
            timers[index] = timers[index].copy(status = TimerStatus.RUNNING)
            // Record session start
            viewModelScope.launch {
                sessionDao.insert(TimerSessionEntity(
                    timerId = id,
                    startTime = System.currentTimeMillis()
                ))
            }
        }
    }

    fun pauseTimer(id: Long) {
        val index = timers.indexOfFirst { it.id == id }
        if (index >= 0) {
            timers[index] = timers[index].copy(status = TimerStatus.PAUSED)
        }
    }

    fun stopTimer(id: Long) {
        val index = timers.indexOfFirst { it.id == id }
        if (index >= 0) {
            val timer = timers[index]
            timers[index] = timer.copy(status = TimerStatus.IDLE, sessionTime = "00:00:00")
            // Update daily summary
            viewModelScope.launch {
                val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    .format(java.util.Date())
                summaryDao.upsert(DailySummaryEntity(
                    date = today,
                    timerId = id,
                    totalDurationMs = 0 // Will be calculated from session
                ))
            }
        }
    }

    fun resetToday(ids: List<Long>) {
        ids.forEach { id ->
            val index = timers.indexOfFirst { it.id == id }
            if (index >= 0) {
                timers[index] = timers[index].copy(dailyTotal = "0h 00m")
            }
        }
        viewModelScope.launch {
            val today = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                .format(java.util.Date())
            ids.forEach { id ->
                summaryDao.deleteByDateAndTimer(today, id)
            }
        }
    }
}
