package com.example.dailytimer.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.example.dailytimer.MainActivity
import com.example.dailytimer.R

class TimerForegroundService : Service() {

    companion object {
        const val CHANNEL_ID = "timer_channel"
        const val NOTIFICATION_ID = 1
        const val ACTION_START = "ACTION_START_TIMER"
        const val ACTION_STOP = "ACTION_STOP_TIMER"
    }

    private var wakeLock: PowerManager.WakeLock? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                acquireWakeLock()
                startForeground(NOTIFICATION_ID, buildNotification("计时进行中…"))
            }
            ACTION_STOP -> {
                releaseWakeLock()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID, "计时器服务", NotificationManager.IMPORTANCE_LOW
        ).apply { setShowBadge(false) }
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)
    }

    private fun buildNotification(text: String): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("每日计时器")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_stat_timer)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun acquireWakeLock() {
        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DailyTimer::WakeLock")
        wakeLock?.acquire(10 * 60 * 1000L) // 10 minutes, re-acquire in app logic
    }

    private fun releaseWakeLock() {
        wakeLock?.let { if (it.isHeld) it.release() }
        wakeLock = null
    }
}
