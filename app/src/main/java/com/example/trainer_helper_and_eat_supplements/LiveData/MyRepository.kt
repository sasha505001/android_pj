package com.example.trainer_helper_and_eat_supplements.LiveData

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.*


class MyRepository(
    private val complexesDao: ComplexesDao,
    private val complexesExercisesDao: ComplexesExercisesDao,
    private val doneExercisesDao: DoneExercisesDao,
    private val exerciseMeasuresDao: ExerciseMeasuresDao,
    private val exercisesDao: ExercisesDao,
    private val measuresDao: MeasuresDao,
    private val trainsDao: TrainsDao,
    private val trainsDoneExercisesDao: TrainsDoneExercisesDao,
) {
    // Упражнения
    val allExercises: LiveData<List<ExercisesData>> = exercisesDao.getAllExercises()
    val allExerciseName : LiveData<List<String>> = exercisesDao.getAllNames()

    // Добавление меры
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMesure(mesure:MeasuresData){
        measuresDao.addAllMeasure(mesure)
    }
    // Меры
    val allMesuresName:LiveData<List<String>> = measuresDao.getAllMeasuresNames()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertExercise(exercisesData: ExercisesData){
        exercisesDao.insertAll(exercisesData)
    }


}