package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.ComplexExerciseData

// DAO (комлпекс - упражнение)
@Dao
interface ComplexExerciseDao {

    // Добавление
    @Insert
    suspend fun insertAll(vararg objs: ComplexExerciseData)

    // Получение всех имен упражнений принадлежащих комплексу(по имени комплекса)
    @Query("SELECT exercise.name FROM complex JOIN complex_exercise on complex.id = complex_id JOIN exercise on exercise_id = exercise.id where complex.name = :complex_name")
    suspend fun getAllExerciseNamesWithComplexName(complex_name:String):List<String>

    @Query("DELETE FROM complex_exercise WHERE complex_id = :complexId")
    suspend fun deleteObjWithComplex(complexId:Int)

    // Очищение таблицы
    @Query("DELETE FROM complex_exercise")
    fun deleteAll()
}