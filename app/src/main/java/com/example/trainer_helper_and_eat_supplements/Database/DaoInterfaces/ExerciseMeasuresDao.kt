package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.DoneExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExerciseMeasuresData

@Dao
interface ExerciseMeasuresDao {
    // Добавение
    @Insert
    suspend fun insertAll(vararg exerciseMeasuresData: ExerciseMeasuresData)


    // Очищение таблицы
    @Query("DELETE FROM exercise_measure")
    fun deleteAll()
}