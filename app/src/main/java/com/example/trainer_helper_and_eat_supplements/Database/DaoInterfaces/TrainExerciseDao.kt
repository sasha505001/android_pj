package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.TrainExerciseData

@Dao
interface TrainExerciseDao {
    // Добавление
    @Insert
    fun insertAll(vararg trainExerciseObjs: TrainExerciseData)

    // Очищение таблицы
    @Query("DELETE FROM train_exercise")
    fun deleteAll()
}