package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import com.example.trainer_helper_and_eat_supplements.Database.Data.ApproachData

// DAO(подход(упражнение - подход)) TODO Добавить создание
@Dao
interface ApproachDao {
    // Добавление подходов
    @Insert
    fun insertAll(vararg objs: ApproachData)
}