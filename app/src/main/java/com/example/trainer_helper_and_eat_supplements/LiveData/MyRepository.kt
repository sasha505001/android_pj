package com.example.trainer_helper_and_eat_supplements.LiveData

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.trainer_helper_and_eat_supplements.Database.DaoInterfaces.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.*


class MyRepository(
    private  val approachDao: ApproachDao,
    private val approachPartOfApproachDao: ApproachPartOfApproachDao,
    private val complexDao: ComplexDao,
    private val complexExerciseDao: ComplexExerciseDao,
    private val exerciseDao: ExerciseDao,
    private val exerciseMeasureDao: ExerciseMeasureDao,
    private val foodAdditiveDao: FoodAdditiveDao,
    private val foodAdditiveTakingTimeAndDoseDao: FoodAdditiveTakingTimeAndDoseDao,
    private val measureDao: MeasureDao,
    private val measureOfFoodAdditiveDao: MeasureOfFoodAdditiveDao,
    private val partOfApproachDao: PartOfApproachDao,
    private val scheduleDao: ScheduleDao,
    private val takingTimeAndDoseDao: TakingTimeAndDoseDao,
    private val trainDao: TrainDao,
    private val trainExerciseDao: TrainExerciseDao,
    private val weekDayDao: WeekDayDao,
    private val weekDayFoodAdditiveDao: WeekDayFoodAdditiveDao,
) {
    // TODO ------------------------------  Упражнения  ---------------------------------
    // Все объекты упражнений
    val allExercises: LiveData<List<ExerciseData>> = exerciseDao.getAllExercises()
    // Все имена упражнений
    val allExerciseName : LiveData<List<String>> = exerciseDao.getAllNames()

    // Добавление упражнения
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertExercise(exercisesData: ExerciseData){
        exerciseDao.insertAllExercises(exercisesData)
    }

    /* Добавление упражнения(упражнения-мера)*/
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addFullyExercise(exerciseData: ExerciseData, measureNames:List<String>){
        exerciseDao.insertNewExercise(exerciseData, measureNames)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateExercise(nameOfOldExercise:String,
                               exercise: ExerciseData,
                               measureNames:List<String>){
        exerciseDao.updateOldExercise(nameOfOldExercise, exercise, measureNames)
    }



    // Удаление упражнения по его имени(без связей
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteExerciseByName(exerciseName: String){
        exerciseDao.deleteExerciseByName(exerciseName)
    }



    // Получение объекта упражнения по имени
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getExerciseByName(exerciseName: String): ExerciseData{
        return exerciseDao.getExerciseByName(exerciseName)
    }

    // Получение объекта упражнения по имени
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteFullyExerciseByName(exerciseName: String){
        exerciseDao.deleteFullyExercise(exerciseName)
    }

    // Получение объекта упражнения по имени
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMesureOfExerciseById(exerciseId:Int):List<String>{
        return exerciseDao.getAllMeasureNamesFromExerciseId(exerciseId)
    }

    // TODO -----------------------------  Меры  -------------------------------------
    // Получение названия всех мер
    // Объекты
    val allMesureData:LiveData<List<MeasureData>> = measureDao.liveGetAllMeasures()
    // Названия
    val allMesuresName:LiveData<List<String>> = measureDao.getAllMeasuresNames()


    // Добавление меры
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMesure(mesure:MeasureData){
        measureDao.addAllMeasure(mesure)
    }


    // Получение объекта меры по имени
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMesureByName(name: String):MeasureData{
        return measureDao.getMeasureByName(name)
    }



    // TODO --------------------------  Упражнение - мера  ------------------------
    // Добавление
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertMesureExercise(data:ExerciseMeasureData){
        exerciseMeasureDao.insertAll(data)
    }


    // TODO ---------------------------------- Комплексы  ------------------------------
    val allComplexesNames:LiveData<List<String>> = complexDao.LiveGetAllComplexesNames()
    val allComplexes:LiveData<List<ComplexData>> = complexDao.liveGetAllComplexes()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFullComplex(complex: ComplexData, namesOfExercises:List<String>){
        complexDao.insertAll(complex)
        val curComplex = complexDao.getComplexByName(complex.name)
        namesOfExercises.forEach(){ exerciseName ->
            val exerciseId = exerciseDao.getExerciseIdByName(exerciseName)
            complexExerciseDao.insertAll(
                ComplexExerciseData(
                    exerciseId,
                    curComplex.id
                )
            )
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun fullDeleteComplex(complexName: String){
        // Получаю комлпекс
        val complex = complexDao.getComplexByName(complexName)
        // Удаляю связи
        complexExerciseDao.deleteObjWithComplex(complex.id)
        // Удаляю сам комлпекс
        complexDao.delete(complex)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllExercisesNamesByComplexName(complexName: String):List<String>{
        return complexExerciseDao.getAllExerciseNamesWithComplexName(complexName)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateComplex(
        oldComplexName: String,
        complex: ComplexData,
        namesOfExercises:List<String>){
        complexDao.updateOldComplex(oldComplexName, complex, namesOfExercises)
    }

    // TODO ---------------------------------- Тренировки  ------------------------------

    // TODO ---------------------------------- Пищ. добавки  ------------------------------
    val allFoodAdditiveNames:LiveData<List<String>> = foodAdditiveDao.liveGetAllFoodAdditiveNames()

    // TODO ---------------------------------- Меры пищ. добавок  ------------------------------
    val allFoodAdditiveMesures = measureOfFoodAdditiveDao.liveGetAllFoodAdditivesMesuresNames()

    // TODO ---------------------------------- Расписания  ------------------------------
    val allScheduleNames = scheduleDao.liveGetAllScheduleNames()

    // TODO ---------------------------------- Дни недели ---------------------------------
    val allDaysOfWeekNames = weekDayDao.getAllWeekDaysNames()

    // Получение мер из упражнения(используя имя)
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getMesuresFromExerciseName(exerciseName: String):List<String>{
        return exerciseDao.getMesuresFromExerciseName(exerciseName)
    }

}