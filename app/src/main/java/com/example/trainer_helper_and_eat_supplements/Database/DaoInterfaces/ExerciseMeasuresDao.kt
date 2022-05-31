package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExerciseMeasuresData

@Dao
interface ExerciseMeasuresDao {
    // Добавение
    @Insert
    suspend fun insertAll(vararg exerciseMeasuresData: ExerciseMeasuresData)

    @Query("SELECT * FROM exercise_measure Where exercise_id = :id")
    suspend fun getAllExerciseMesureById(id:Int) :List<ExerciseMeasuresData>

    // Очищение таблицы
    @Query("DELETE FROM exercise_measure")
    fun deleteAll()
}