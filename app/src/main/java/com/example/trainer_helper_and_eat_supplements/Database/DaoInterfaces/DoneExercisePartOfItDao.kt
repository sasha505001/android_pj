package com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trainer_helper_and_eat_supplements.Database.Data.DoneExercisePartOfItData
import com.example.trainer_helper_and_eat_supplements.Database.Data.PartOfDoneExercisesData

@Dao
interface DoneExercisePartOfItDao {



    // Добавление
    @Insert
    fun insertAll(vararg DoneExercisePartOfItData: DoneExercisePartOfItData)

}