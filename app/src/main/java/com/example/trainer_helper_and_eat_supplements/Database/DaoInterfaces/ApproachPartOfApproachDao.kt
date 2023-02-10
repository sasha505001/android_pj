package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import com.example.trainer_helper_and_eat_supplements.Database.Data.ApproachPartOfApproachData


// DAO(подход-часть инфы о подходе)
@Dao
interface ApproachPartOfApproachDao {

    // Добавление
    @Insert
    fun insertAll(vararg objs: ApproachPartOfApproachData)

}