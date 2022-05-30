package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExerciseMeasuresData
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.Data.MeasuresData

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
    suspend fun insertAllExercises(vararg exercises: ExercisesData)

    // Удаление упражнения
    @Delete
    fun deleteExercise(exercises: ExercisesData)

    // Очищение таблицы
    @Query("DELETE FROM exercise")
    fun deleteAllExercises()

    @Query("DELETE FROM exercise WHERE name = :name")
    suspend fun deleteExerciseByName(name: String)

    @Transaction
    open suspend fun insertNewExercise(exercise: ExercisesData, measureNames:List<String>){
        this.insertAllExercises(exercise)
        val exerciseId = getExerciseByName(exercise.name).id
        for (item in measureNames){
            val mesureData = getMeasureByName(item)
            insertAllExerciseMesureData(
                ExerciseMeasuresData(exerciseId, mesureData.id)
            )
        }
    }

    // Добавение
    @Insert
    suspend fun insertAllExerciseMesureData(vararg exerciseMeasuresData: ExerciseMeasuresData)

    // Получение меры измерения по имени
    @Query("SELECT * FROM Measure WHERE name = :name")
    suspend fun getMeasureByName(name:String):MeasuresData

}