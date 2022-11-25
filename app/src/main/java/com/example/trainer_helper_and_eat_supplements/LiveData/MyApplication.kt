package com.example.trainer_helper_and_eat_supplements.LiveData

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trainer_helper_and_eat_supplements.CONSTANTS
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
            database.ComplexesDao(),
            database.ComplexesExercisesDao(),
            database.DoneExercisePartOfItDao(),
            database.ExerciseMeasuresDao(),
            database.ExercisesDao(),
            database.FoodAdditiveDao(),
            database.MeasureOfFoodAdditiveDao(),
            database.MeasuresDao(),
            database.PartOfDoneExercisesDao(),
            database.ScheduleDao(),
            database.TakingTimeDao(),
            database.TakingTimeFoodAdditiveDao(),
            database.TrainsDao(),
            database.TrainsDoneExercisesDao(),
            database.WeekDaysDao(),
            database.WeekDaysFoodAdditiveDao()

        )
    }
}


