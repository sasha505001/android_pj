package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.ComplexesExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData

@Dao
interface ComplexesExercisesDao {

    // Добавление
    @Insert
    suspend fun insertAll(vararg complexerExercisesData: ComplexesExercisesData)

    // Очищение таблицы
    @Query("DELETE FROM complex_exercise")
    fun deleteAll()
}