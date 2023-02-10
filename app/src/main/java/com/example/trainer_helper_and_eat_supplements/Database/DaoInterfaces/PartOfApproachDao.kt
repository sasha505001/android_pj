package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import com.example.trainer_helper_and_eat_supplements.Database.Data.PartOfApproachData

// DAO(часть инф. о подходе)
@Dao
interface PartOfApproachDao {
    @Insert
    suspend fun insertAllPartsOfApprroache(vararg obj: PartOfApproachData)
}