package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.ScheduleData

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule")
    suspend fun getAllScheduleObj():List<ScheduleData>

    @Insert
    suspend fun insertAllSchedule(vararg obj: ScheduleData)
}