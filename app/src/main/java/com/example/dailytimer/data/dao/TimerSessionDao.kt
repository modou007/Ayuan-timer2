package com.example.dailytimer.data.dao

import androidx.room.*
import com.example.dailytimer.data.entity.TimerSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerSessionDao {
    @Query("SELECT * FROM timer_sessions WHERE timerId = :timerId ORDER BY startTime DESC")
    fun getByTimer(timerId: Long): Flow<List<TimerSessionEntity>>

    @Insert
    suspend fun insert(session: TimerSessionEntity): Long

    @Update
    suspend fun update(session: TimerSessionEntity)

    @Query("DELETE FROM timer_sessions WHERE timerId = :timerId")
    suspend fun deleteByTimer(timerId: Long)
}
