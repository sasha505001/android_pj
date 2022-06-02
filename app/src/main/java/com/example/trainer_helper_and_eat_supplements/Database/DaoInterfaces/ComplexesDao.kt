package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.ComplexesData

@Dao
interface ComplexesDao {
    // Получения списка строк всех комплексов
    @Query("SELECT * FROM complex")
    fun getAllComplexes():LiveData<List<ComplexesData>>

    @Query("SELECT name FROM complex")
    fun getAllComplexesNames():LiveData<List<String>>

    // Получения упражнения по имени
    @Query("SELECT * FROM complex WHERE name = :name")
    suspend fun getComplex(name:String):ComplexesData

    // Заполнение комплекса
    @Insert
    suspend fun insertAll(vararg exercises: ComplexesData)

    // Удаление комплекса
    @Delete
    fun delete(exercises: ComplexesData)

    // Очищение таблицы
    @Query("DELETE FROM complex")
    fun deleteAll()

}