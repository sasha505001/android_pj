package com.example.trainer_helper_and_eat_supplements.LiveData

import android.app.Application
import android.content.Context
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase

class MyApplication(context: Context) {
    //val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy{
        MyDatabase.getDatabase(context)
    }

    // Все на счет упражнений
    val myRep by lazy{
        MyRepository(
            database.ComplexesDao(),
            database.ComplexesExercisesDao(),
            database.DoneExercisesDao(),
            database.ExerciseMeasuresDao(),
            database.ExercisesDao(),
            database.MeasuresDao(),
            database.TrainsDao(),
            database.TrainsDoneExercisesDao(),
        )
    }





}


