package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExerciseMeasureData

// DAO(упражнение-мера)
@Dao
interface ExerciseMeasureDao {
    // Добавение
    @Insert
    suspend fun insertAll(vararg objs: ExerciseMeasureData)

    // Получение все связей упражнение-мера по id упражнения
    @Query("SELECT * FROM exercise_measure Where exercise_id = :id")
    suspend fun getAllExerciseMesureById(id:Int) :List<ExerciseMeasureData>

    // Очищение таблицы
    @Query("DELETE FROM exercise_measure")
    fun deleteAll()
}