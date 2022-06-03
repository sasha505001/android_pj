package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.MeasureOfFoodAdditiveData

@Dao
interface MeasureOfFoodAdditiveDao {
    @Query("SELECT name FROM measure_of_food_additive")
    fun liveGetAllFoodAdditivesMesuresNames():LiveData<List<String>>

    @Query("SELECT * FROM measure_of_food_additive")
    suspend fun getAllFoodAdditiveMesuresObj():List<MeasureOfFoodAdditiveData>

    @Insert
    suspend fun insertAllFoodAdditiveMesures(vararg obj:MeasureOfFoodAdditiveData)

}