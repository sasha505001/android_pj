package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.ComplexData
import com.example.trainer_helper_and_eat_supplements.Database.Data.ComplexExerciseData

// DAO (комплекса)
@Dao
interface ComplexDao {

    // Получения списка строк всех комплексов
    @Query("SELECT * FROM complex")
    fun liveGetAllComplexes():LiveData<List<ComplexData>>

    // Получение имён всех комплексов
    @Query("SELECT name FROM complex")
    fun LiveGetAllComplexesNames():LiveData<List<String>>

    // Получение комплекса по имени
    @Query("SELECT * FROM complex WHERE name = :name")
    suspend fun getComplexByName(name:String):ComplexData

    // Обновление комплекса
    @Update
    suspend fun updateComplex(exercises: ComplexData)

    // Заполнение комплекса
    @Insert
    suspend fun insertAll(vararg objs: ComplexData)

    // Удаление комплекса
    @Delete
    suspend fun delete(exercises: ComplexData)

    // Очищение таблицы
    @Query("DELETE FROM complex")
    fun deleteAll()


    // Обновление комплекса в бд
    @Transaction
    suspend fun updateOldComplex(oldComplexName: String,
                                 complex: ComplexData,
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
                ComplexExerciseData(
                    exerciseId,
                    newComplex.id
                )
            )
        }
    }


    // Все что ниже добавлино ради обновления комплекса ибо все действия нужно выполнять в течении одной транзакции
    // Добавление связи комплекс - упражнение
    @Insert
    suspend fun insertAllComplexExercises(vararg complexerExercisesData: ComplexExerciseData)

    // получаение id упражнения по его имени
    @Query("SELECT ID FROM EXERCISE WHERE NAME = :name")
    suspend fun getExerciseIdByNameSuspend(name: String):Int

    // удаление связи упражнение комплекс - упражнение по id комплекса
    @Query("DELETE FROM complex_exercise WHERE complex_id = :complexId")
    suspend fun deleteObjWithComplex(complexId:Int)
}