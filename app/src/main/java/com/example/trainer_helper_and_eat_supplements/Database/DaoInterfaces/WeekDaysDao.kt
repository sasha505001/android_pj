package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.WeekDaysData

@Dao
interface WeekDaysDao {
    // Получение всех объектов дней недели из бд
    @Query("SELECT * FROM week_days")
    suspend fun getAllWeekDaysObj():List<WeekDaysData>

    // Добавить все дни недели
    @Insert
    suspend fun insertAllWeekDays(vararg weekDaysData: WeekDaysData)

    // Получить все названия дней недели
    @Query("Select name from week_days")
    fun getAllWeekDaysNames():LiveData<List<String>>
}