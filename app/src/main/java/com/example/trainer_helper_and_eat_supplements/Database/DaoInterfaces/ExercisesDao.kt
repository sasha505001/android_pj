package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import java.util.concurrent.Flow

@Dao
interface ExercisesDao {

    // Получение всех упражнений
    @Query("SELECT * FROM exercise")
    fun getAllExercises(): LiveData<List<ExercisesData>>

    // Получение списка названий всех упражнений
    @Query("SELECT name FROM exercise")
    fun getAllNames():LiveData<List<String>>

    // Получения упражнения по имени
    @Query("SELECT * FROM exercise WHERE name = :name")
    suspend fun getExerciseByName(name:String):ExercisesData

    // Добавление упражнения
    @Insert
    suspend fun insertAll(vararg exercises: ExercisesData)

    // Удаление упражнения
    @Delete
    fun delete(exercises: ExercisesData)

    // Очищение таблицы
    @Query("DELETE FROM exercise")
    fun deleteAll()

    @Query("DELETE FROM exercise WHERE name = :name")
    suspend fun deleteByName(name: String)

}