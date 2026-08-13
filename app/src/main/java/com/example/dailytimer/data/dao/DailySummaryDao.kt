package com.example.dailytimer.data.dao

import androidx.room.*
import com.example.dailytimer.data.entity.DailySummaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailySummaryDao {
    @Query("SELECT * FROM daily_summaries WHERE date = :date")
    fun getByDate(date: String): Flow<List<DailySummaryEntity>>

    @Query("SELECT * FROM daily_summaries WHERE date BETWEEN :start AND :end")
    fun getRange(start: String, end: String): Flow<List<DailySummaryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(summary: DailySummaryEntity)

    @Query("DELETE FROM daily_summaries WHERE date = :date AND timerId = :timerId")
    suspend fun deleteByDateAndTimer(date: String, timerId: Long)
}
