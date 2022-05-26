package com.example.trainer_helper_and_eat_supplements

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase

class MyApp : Application() {
    val database by lazy{
        MyDatabase.getDatabase(this)
    }

    val allExercises: LiveData<List<ExercisesData>> = database?.ExercisesDao()?.getAllExercises()!!

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertExercise(exercisesData: ExercisesData){
        database?.ExercisesDao()?.insertAll(exercisesData)
    }
}


