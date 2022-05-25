package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.TrainsDoneExerciseData

@Dao
interface TrainsDoneExercisesDao {
    // Добавление
    @Insert
    fun insertAll(vararg trainsDoneExercise: TrainsDoneExerciseData)

    // Очищение таблицы
    @Query("DELETE FROM train_done_exercise")
    fun deleteAll()
}