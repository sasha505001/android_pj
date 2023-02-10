package com.example.trainer_helper_and_eat_supplements.LiveData

import android.content.Context
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication(context: Context) {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy{
        MyDatabase.getDatabase(context, applicationScope)
    }

    // Все на счет упражнений
    val myRep by lazy{
        MyRepository(
            database.ApproachDao(),
            database.ApproachPartOfApproachDao(),
            database.ComplexDao(),
            database.ComplexExerciseDao(),
            database.ExerciseDao(),
            database.ExerciseMeasureDao(),
            database.FoodAdditiveDao(),
            database.FoodAdditiveTakingTimeAndDoseDao(),
            database.MeasureDao(),
            database.MeasureOfFoodAdditiveDao(),
            database.PartOfApproachDao(),
            database.ScheduleDao(),
            database.TakingTimeAndDoseDao(),
            database.TrainDao(),
            database.TrainExerciseDao(),
            database.WeekDayDao(),
            database.WeekDayFoodAdditiveDao()
        )
    }
}


