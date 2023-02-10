package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.TrainData

// DAO(тренировка)
@Dao
interface TrainDao {

    // Получение упражнения по имени
    @Query("Select * FROM Train WHERE name = :train_name")
    fun getTrainByName(train_name:String):TrainData

    // Добавление новой тренировки
    @Insert
    fun insertAll(vararg trains:TrainData)

    // Очищение таблицы
    @Query("DELETE FROM train")
    fun deleteAll()
}