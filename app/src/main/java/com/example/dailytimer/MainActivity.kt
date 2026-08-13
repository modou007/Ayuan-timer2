package com.example.dailytimer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailytimer.data.db.AppDatabase
import com.example.dailytimer.service.FloatingBallService
import com.example.dailytimer.service.TimerForegroundService
import com.example.dailytimer.ui.calendar.CalendarScreen
import com.example.dailytimer.ui.dialogs.CreateTimerDialog
import com.example.dailytimer.ui.dialogs.DeleteConfirmDialog
import com.example.dailytimer.ui.dialogs.TodayResetSheet
import com.example.dailytimer.ui.home.HomeScreen
import com.example.dailytimer.ui.stats.StatisticsScreen
import com.example.dailytimer.ui.stopwatch.StopwatchScreen
import com.example.dailytimer.ui.theme.DailyTimerTheme
import com.example.dailytimer.viewmodel.MainViewModel
import com.example.dailytimer.viewmodel.StopwatchViewModel
import com.example.dailytimer.viewmodel.TimerStatus

class MainActivity : ComponentActivity() {

    private val mainVm: MainViewModel by viewModels {
        object : androidx.lifecycle.ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                val db = AppDatabase.getInstance(this@MainActivity)
                return MainViewModel(db.timerDao(), db.timerSessionDao(), db.dailySummaryDao()) as T
            }
        }
    }

    private val stopwatchVm: StopwatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyTimerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppContent(mainVm, stopwatchVm)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(mainVm: MainViewModel, stopwatchVm: StopwatchViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    fun startTimerService() {
        val intent = Intent(context, TimerForegroundService::class.java).apply {
            action = TimerForegroundService.ACTION_START
        }
        ContextCompat.startForegroundService(context, intent)
    }

    fun stopTimerService() {
        val hasRunning = mainVm.timers.any { it.status == TimerStatus.RUNNING }
        if (!hasRunning) {
            val intent = Intent(context, TimerForegroundService::class.java).apply {
                action = TimerForegroundService.ACTION_STOP
            }
            context.startService(intent)
        }
    }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                timers = mainVm.timers,
                onAddTimer = { mainVm.showCreateDialog.value = true },
                onResetToday = { mainVm.showResetSheet.value = true },
                onStart = { id ->
                    mainVm.startTimer(id)
                    startTimerService()
                },
                onPause = { mainVm.pauseTimer(it) },
                onStop = { id ->
                    mainVm.stopTimer(id)
                    stopTimerService()
                },
                onDelete = { timer ->
                    mainVm.deleteTarget.value = timer
                    mainVm.showDeleteDialog.value = true
                },
                onNavigateStats = { navController.navigate("stats") },
                onNavigateCalendar = { navController.navigate("calendar") },
                onNavigateStopwatch = { navController.navigate("stopwatch") }
            )
        }
        composable("stats") {
            StatisticsScreen(onBack = { navController.popBackStack() })
        }
        composable("calendar") {
            CalendarScreen(onBack = { navController.popBackStack() })
        }
        composable("stopwatch") {
            StopwatchScreen(
                viewModel = stopwatchVm,
                onBack = { navController.popBackStack() }
            )
        }
    }

    // Create Dialog (Bottom Sheet)
    if (mainVm.showCreateDialog.value) {
        androidx.compose.material3.ModalBottomSheet(
            onDismissRequest = { mainVm.showCreateDialog.value = false },
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            dragHandle = null
        ) {
            CreateTimerDialog(
                onDismiss = { mainVm.showCreateDialog.value = false },
                onCreate = { name, type, color ->
                    mainVm.addTimer(name, type, color)
                    mainVm.showCreateDialog.value = false
                }
            )
        }
    }

    // Delete Dialog
    if (mainVm.showDeleteDialog.value) {
        mainVm.deleteTarget.value?.let { timer ->
            androidx.compose.ui.window.Dialog(
                onDismissRequest = { mainVm.showDeleteDialog.value = false },
                properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
            ) {
                DeleteConfirmDialog(
                    timer = timer,
                    onDismiss = { mainVm.showDeleteDialog.value = false },
                    onConfirm = {
                        mainVm.deleteTimer(timer.id)
                        mainVm.showDeleteDialog.value = false
                    }
                )
            }
        }
    }

    // Today Reset Sheet
    if (mainVm.showResetSheet.value) {
        androidx.compose.material3.ModalBottomSheet(
            onDismissRequest = { mainVm.showResetSheet.value = false },
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            dragHandle = null
        ) {
            TodayResetSheet(
                timers = mainVm.timers,
                onDismiss = { mainVm.showResetSheet.value = false },
                onReset = { ids ->
                    mainVm.resetToday(ids)
                    mainVm.showResetSheet.value = false
                }
            )
        }
    }
}
