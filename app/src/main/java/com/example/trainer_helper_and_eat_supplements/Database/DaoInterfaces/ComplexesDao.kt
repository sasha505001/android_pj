package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.ComplexesData
import com.example.trainer_helper_and_eat_supplements.Database.Data.ComplexesExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData

@Dao
interface ComplexesDao {
    // Получения списка строк всех комплексов
    @Query("SELECT * FROM complex")
    fun liveGetAllComplexes():LiveData<List<ComplexesData>>

    @Query("SELECT name FROM complex")
    fun LiveGetAllComplexesNames():LiveData<List<String>>

    // Получения упражнения по имени
    @Query("SELECT * FROM complex WHERE name = :name")
    suspend fun getComplexByName(name:String):ComplexesData

    @Update
    suspend fun updateComplex(exercises: ComplexesData)

    // Заполнение комплекса
    @Insert
    suspend fun insertAll(vararg exercises: ComplexesData)

    // Удаление комплекса
    @Delete
    suspend fun delete(exercises: ComplexesData)

    // Очищение таблицы
    @Query("DELETE FROM complex")
    fun deleteAll()


    // Обновление в бд
    @Transaction
    suspend fun updateOldComplex(oldComplexName: String,
                                      complex: ComplexesData,
                                      namesOfExercises:List<String>
    ){
        val newComplex = complex
        val oldComplex = this.getComplexByName(oldComplexName)
        newComplex.id = oldComplex.id

        deleteObjWithComplex(oldComplex.id)

        updateComplex(newComplex)
        namesOfExercises.forEach(){ exerciseName ->
            val exerciseId = getExerciseIdByNameSuspend(exerciseName)
            insertAllComplexExercises(
                ComplexesExercisesData(
                    exerciseId,
                    newComplex.id
                )
            )
        }
    }


    // Все что ниже добавлино ради обновления комплекса ибо все действия нужно выполнять в течении одной транзакции
    @Insert
    suspend fun insertAllComplexExercises(vararg complexerExercisesData: ComplexesExercisesData)

    @Query("SELECT ID FROM EXERCISE WHERE NAME = :name")
    suspend fun getExerciseIdByNameSuspend(name: String):Int

    @Query("DELETE FROM complex_exercise WHERE complex_id = :complexId")
    suspend fun deleteObjWithComplex(complexId:Int)
}