package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.MeasuresData

@Dao
interface MeasuresDao {
    // Получение названий всех мер измерения
    @Query("SELECT * FROM measure")
    fun liveGetAllMeasures(): LiveData<List<MeasuresData>>

    // Получение названий всех мер измерения
    @Query("SELECT * FROM measure")
    fun getAllMeasures(): List<MeasuresData>

    @Query("SELECT name FROM measure")
    fun getAllMeasuresNames(): LiveData<List<String>>

    // Получение меры измерения по имени
    @Query("SELECT * FROM Measure WHERE name = :name")
    suspend fun getMeasureByName(name:String):MeasuresData

    // Добавление меры
    @Insert
    suspend fun addAllMeasure(vararg measure: MeasuresData)

    // Удаление меры
    @Delete
    suspend fun deleteMeasure(measure: MeasuresData)

    // Очищение таблицы
    @Query("DELETE FROM measure")
    suspend fun deleteAll()



}