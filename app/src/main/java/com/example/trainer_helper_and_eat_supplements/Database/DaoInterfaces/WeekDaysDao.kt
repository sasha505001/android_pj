package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.WeekDaysData

@Dao
interface WeekDaysDao {
    @Query("SELECT * FROM week_days")
    suspend fun getAllWeekDaysObj():List<WeekDaysData>

    @Insert
    suspend fun insertAllWeekDays(vararg weekDaysData: WeekDaysData)
}