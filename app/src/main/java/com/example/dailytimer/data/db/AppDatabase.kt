package com.example.dailytimer.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dailytimer.data.dao.DailySummaryDao
import com.example.dailytimer.data.dao.TimerDao
import com.example.dailytimer.data.dao.TimerSessionDao
import com.example.dailytimer.data.entity.DailySummaryEntity
import com.example.dailytimer.data.entity.TimerEntity
import com.example.dailytimer.data.entity.TimerSessionEntity

@Database(
    entities = [TimerEntity::class, TimerSessionEntity::class, DailySummaryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao
    abstract fun timerSessionDao(): TimerSessionDao
    abstract fun dailySummaryDao(): DailySummaryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "daily_timer.db"
                ).build().also { INSTANCE = it }
            }
    }
}
