package com.example.dailytimer.data.dao

import androidx.room.*
import com.example.dailytimer.data.entity.TimerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDao {
    @Query("SELECT * FROM timers ORDER BY createdAt DESC")
    fun getAll(): Flow<List<TimerEntity>>

    @Insert
    suspend fun insert(timer: TimerEntity): Long

    @Delete
    suspend fun delete(timer: TimerEntity)

    @Query("DELETE FROM timers WHERE id = :id")
    suspend fun deleteById(id: Long)
}
