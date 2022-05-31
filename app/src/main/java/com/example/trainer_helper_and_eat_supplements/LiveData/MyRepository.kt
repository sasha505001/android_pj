package com.example.trainer_helper_and_eat_supplements.LiveData

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.*


class MyRepository(
    private val complexesDao: ComplexesDao,
    private val complexesExercisesDao: ComplexesExercisesDao,
    private val doneExercisePartOfItDao: DoneExercisePartOfItDao,
    private val exerciseMeasuresDao: ExerciseMeasuresDao,
    private val exercisesDao: ExercisesDao,
    private val foodAdditiveDao: FoodAdditiveDao,
    private val measureOfFoodAdditiveDao: MeasureOfFoodAdditiveDao,
    private val measuresDao: MeasuresDao,
    private val partOfDoneExercisesDao: PartOfDoneExercisesDao,
    private val scheduleDao: ScheduleDao,
    private val takingTimeDao: TakingTimeDao,
    private val takingTimeFoodAdditiveDao: TakingTimeFoodAdditiveDao,
    private val trainsDao: TrainsDao,
    private val trainsDoneExercisesDao: TrainsDoneExercisesDao,
    private val weekDaysDao: WeekDaysDao,
    private val weekDaysFoodAdditiveDao: WeekDaysFoodAdditiveDao,
) {
    // TODO ------------------------------  Упражнения  ---------------------------------
    // Все объекты упражнений
    val allExercises: LiveData<List<ExercisesData>> = exercisesDao.getAllExercises()
    // Все имена упражнений
    val allExerciseName : LiveData<List<String>> = exercisesDao.getAllNames()

    // Добавление упражнения
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertExercise(exercisesData: ExercisesData){
        exercisesDao.insertAllExercises(exercisesData)
    }

    /* Добавление упражнения(упражнения-мера)*/
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addFullyExercise(exerciseData: ExercisesData, measureNames:List<String>){
        exercisesDao.insertNewExercise(exerciseData, measureNames)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateExercise(nameOfOldExercise:String,
                               exercise: ExercisesData,
                               measureNames:List<String>){
        exercisesDao.updateOldExercise(nameOfOldExercise, exercise, measureNames)
    }



    // Удаление упражнения по его имени
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteExerciseByName(exerciseName: String){
        exercisesDao.deleteExerciseByName(exerciseName)
    }

    // Получение объекта упражнения по имени
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getExerciseByName(exerciseName: String): ExercisesData{
        return exercisesDao.getExerciseByName(exerciseName)
    }

    // Получение объекта упражнения по имени
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMesureOfExerciseById(exerciseId:Int):List<String>{
        return exercisesDao.getAllMesureNamesFromExerciseId(exerciseId)
    }

    // TODO -----------------------------  Меры  -------------------------------------
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


    // Получение объекта меры по имени
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMesureByName(name: String):MeasuresData{
        return measuresDao.getMeasureByName(name)
    }



    // TODO --------------------------  Упражнение - мера  ------------------------
    // Добавление
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMesureExercise(data:ExerciseMeasuresData){
        exerciseMeasuresDao.insertAll(data)
    }


    // TODO ---------------------------------- Комплексы  ------------------------------

    // TODO ---------------------------------- Тренировки  ------------------------------
    // TODO ---------------------------------- Пищ. добавки  ------------------------------
}