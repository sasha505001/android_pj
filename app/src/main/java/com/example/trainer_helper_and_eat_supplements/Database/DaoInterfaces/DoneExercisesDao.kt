package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.ComplexesExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.Data.DoneExercisesData

@Dao
interface DoneExercisesDao {

    // Получение всех упражнений
    @Query("SELECT * FROM done_exercise")
    fun getAll():List<DoneExercisesData>

    // Добавление
    @Insert
    fun insertAll(vararg doneExercisesData: DoneExercisesData)

    // Очищение таблицы
    @Query("DELETE FROM done_exercise")
    fun deleteAll()
}