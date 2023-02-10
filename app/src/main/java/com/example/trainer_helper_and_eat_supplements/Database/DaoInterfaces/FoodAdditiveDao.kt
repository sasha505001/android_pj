package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.FoodAdditiveData
import com.example.trainer_helper_and_eat_supplements.Database.Data.MeasureOfFoodAdditiveData

// DAO(пищевая добавка)
@Dao
interface FoodAdditiveDao {
    // Получить имена всех пищевых добавок
    @Query("SELECT name FROM food_additive")
    fun liveGetAllFoodAdditiveNames():LiveData<List<String>>

    // Добавить множество пищевых добавок
    @Insert
    suspend fun insertFoodAdditives(vararg objs: FoodAdditiveData)


}