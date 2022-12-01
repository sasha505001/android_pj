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

    @Query("Select name From EXERCISE_MEASURE JOIN measure on id = measure_id where exercise_id = :id ")
    suspend fun getAllMesureNamesFromExerciseId(id:Int):List<String>

    // Получение списка названий всех упражнений
    @Query("SELECT name FROM exercise")
    fun getAllNames():LiveData<List<String>>

    @Query("SELECT ID FROM EXERCISE WHERE NAME = :name")
    suspend fun getExerciseIdByName(name: String):Int

    // Получения упражнения по имени
    @Query("SELECT * FROM exercise WHERE name = :name")
    suspend fun getExerciseByName(name:String):ExercisesData

    // Добавление упражнения
    @Insert
    suspend fun insertAllExercises(vararg exercises: ExercisesData)

    @Update
    suspend fun updateExercise(exercises: ExercisesData)

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


    // Обновление в бд
    @Transaction
    open suspend fun updateOldExercise(nameOfOldExercise:String,
                                       exercise: ExercisesData,
                                       measureNames:List<String>
    ){
        val updatingExerciseId = getExerciseIdByName(nameOfOldExercise)
        val newExercise = exercise
        newExercise.id = updatingExerciseId
        updateExercise(newExercise)
        deleteAllExerciseMesureByExerciseId(updatingExerciseId)
        for (item in measureNames){
            val mesureData = getMeasureByName(item)
            insertAllExerciseMesureData(
                ExerciseMeasuresData(updatingExerciseId, mesureData.id)
            )
        }
    }

    @Transaction
    open suspend fun deleteFullyExercise(name:String){
        val oldExerciseId = getExerciseIdByName(name)
        deleteAllExerciseMesureByExerciseId(oldExerciseId)
        deleteExerciseFromAllComplexes(oldExerciseId)
        deleteExercise(getExerciseByName(name))
    }

    // Для удаления упражнения из всех комплексов
    @Query("DELETE FROM complex_exercise WHERE exercise_id = :id")
    suspend fun deleteExerciseFromAllComplexes(id:Int)

    // Удаление всех мера упражнение из бд
    @Query("DELETE FROM exercise_measure Where exercise_id = :id")
    suspend fun deleteAllExerciseMesureByExerciseId(id:Int)

    // Добавение мера - упражнение
    @Insert
    suspend fun insertAllExerciseMesureData(vararg exerciseMeasuresData: ExerciseMeasuresData)

    // Получение меры измерения по имени
    @Query("SELECT * FROM Measure WHERE name = :name")
    suspend fun getMeasureByName(name:String):MeasuresData

    // Получение мер из упражнения благодаря имени
    @Query("SELECT measure.name FROM measure " +
            "JOIN exercise_measure ON measure_id = measure.id " +
            "JOIN Exercise on exercise_id = exercise.id " +
            "where :name = exercise.name ")
    suspend fun getMesuresFromExerciseName(name: String):List<String>

}