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
    // ------------------------------  Упражнения  ------------------------------------
    val allExercises: LiveData<List<ExercisesData>> = exercisesDao.getAllExercises()
    val allExerciseName : LiveData<List<String>> = exercisesDao.getAllNames()

    // Добавление упражнения
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertExercise(exercisesData: ExercisesData){
        exercisesDao.insertAllExercises(exercisesData)
    }

    // Удаление упражнения по его имени
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteExerciseByName(exerciseName: String){
        exercisesDao.deleteExerciseByName(exerciseName)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getExerciseByName(exerciseName: String): ExercisesData{
        return exercisesDao.getExerciseByName(exerciseName)
    }

    // -----------------------------  Меры  -------------------------------------
    // Получение названия всех мер
    // Объекты
    val allMesureData:LiveData<List<MeasuresData>> = measuresDao.getAllMeasures()
    // Названия
    val allMesuresName:LiveData<List<String>> = measuresDao.getAllMeasuresNames()


    // Добавление меры
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMesure(mesure:MeasuresData){
        measuresDao.addAllMeasure(mesure)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMesureByName(name: String):MeasuresData{
        return measuresDao.getMeasureByName(name)
    }

    // Добавление упражнения
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addFullyExercise(exerciseData: ExercisesData, measureNames:List<String>){
        exercisesDao.insertNewExercise(exerciseData, measureNames)
    }



    // --------------------------  Упражнение - мера  ------------------------
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMesureExercise(data:ExerciseMeasuresData){
        exerciseMeasuresDao.insertAll(data)
    }

}