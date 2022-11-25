package com.example.trainer_helper_and_eat_supplements

import androidx.lifecycle.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.*
import com.example.trainer_helper_and_eat_supplements.LiveData.MyRepository
import kotlinx.coroutines.launch


class MyDataModel(private val myRep: MyRepository): ViewModel(){

    // TODO сделать чтобы вид текущего листа запоминался

    // TODO -----------------------------  Упражнения  --------------------------------------------
    // Все объекты упражнений
    val allExercisesData: LiveData<List<ExercisesData>> = myRep.allExercises
    // Все имена упражнений
    val allExercisesName: LiveData<List<String>> = myRep.allExerciseName

    // Добавление упражнения(без связей)
    fun insertExercise(exercisesData: ExercisesData) = viewModelScope.launch(){
        myRep.insertExercise(exercisesData)
    }

    // Добавление упражнения полностью со всеми связями(меры)
    fun addFullyExercise(exerciseData: ExercisesData, measureNames:List<String>) =
        viewModelScope.launch {
            myRep.addFullyExercise(exerciseData, measureNames)
        }

    // Фуникция редактирования упражнения
    fun updateOldExercise(nameOfOldExercise:String,
                          exercise: ExercisesData,
                          measureNames:List<String>) =
        viewModelScope.launch {
            myRep.updateExercise(nameOfOldExercise, exercise, measureNames)
        }

    // Удаление упражнения(без связей)
    fun deleteExerciseByName(exerciseName:String) = viewModelScope.launch {
        myRep.deleteExerciseByName(exerciseName)
    }

    // Полностью удаляю упражнение включая все его связи
    fun deleteFullyExerciseByName(exerciseName:String) = viewModelScope.launch {
        myRep.deleteFullyExerciseByName(exerciseName)
    }

    // Получение упражнения по имени
    fun getExerciseByName(exerciseName: String):LiveData<ExercisesData>{
        val result = MutableLiveData<ExercisesData>()
        viewModelScope.launch {
            val returnRepo = myRep.getExerciseByName(exerciseName)
            result.postValue(returnRepo)
        }
        return result
    }



    // TODO --------------------------------- Меры ----------------------------------------------
    // Все объекты мер
    val allMesures:LiveData<List<MeasuresData>> = myRep.allMesureData
    // Список всех мер
    val allMesuresName: LiveData<List<String>> = myRep.allMesuresName

    // Получение меры по имени
    fun getMesureByName(name:String): LiveData<MeasuresData>{
        val result = MutableLiveData<MeasuresData>()
        viewModelScope.launch {
            val returnRepo = myRep.getMesureByName(name)
            result.postValue(returnRepo)
        }
        return result
    }

    // Получение всех мер(строка) из упражнения
    fun getMesuresFromExerciseId(exerciseId:Int): LiveData<List<String>>{
        val result = MutableLiveData<List<String>>()
        viewModelScope.launch {
            val returnRepo = myRep.getMesureOfExerciseById(exerciseId)
            result.postValue(returnRepo)
        }
        return result
    }

    // TODO --------------------------------------- Мера - упражнение -----------------------------
    // Добавление меры
    fun insertExerciseMeasure(data:ExerciseMeasuresData)= viewModelScope.launch(){
        myRep.insertMesureExercise(data)
    }

    // TODO ---------------------------------- Комплексы  ------------------------------
    val allComplexesNames = myRep.allComplexesNames
    val allComplexes = myRep.allComplexes

    fun insertFullComplex(complex: ComplexesData, namesOfExercises:List<String>)
    = viewModelScope.launch {
        myRep.insertFullComplex(complex, namesOfExercises)
    }

    fun deleteFullComplex(complexName:String) = viewModelScope.launch {
        myRep.fullDeleteComplex(complexName)
    }

    fun getExercisesNamesByComplexName(complexName: String):LiveData<List<String>>{
        val result = MutableLiveData<List<String>>()
        viewModelScope.launch {
            val returnRepo = myRep.getAllExercisesNamesByComplexName(complexName)
            result.postValue(returnRepo)
        }
        return result
    }

    fun updateComplex(oldComplexName: String,
                      complex: ComplexesData,
                      namesOfExercises:List<String>) = viewModelScope.launch {
                          myRep.updateComplex(oldComplexName, complex, namesOfExercises)
    }
    // TODO ---------------------------------- Тренировки  ------------------------------
    // TODO ---------------------------------- Пищ. добавки  ------------------------------
    val allFoodAdditiveNames = myRep.allFoodAdditiveNames

    // TODO ---------------------------------- Меры пищ. добавок  ------------------------------
    val allFoodAdditiveMeasureNames = myRep.allFoodAdditiveMesures

    // TODO ---------------------------------- Расписания  ------------------------------
    val allScheduleNames = myRep.allScheduleNames

    // -------------------------------------- Другое ---------------------------------

    // TODO специально для выбора упражнений из комплекса
    val allBoolExercise:MutableLiveData<BooleanArray> by lazy {
        MutableLiveData<BooleanArray>()
    }

    // TODO специально для времени
    val allTakingTimeForFoodAdditive:MutableLiveData<MutableList<TakingTimeData>> by lazy{
        MutableLiveData<MutableList<TakingTimeData>>()
    }

    val editTime:MutableLiveData<TakingTimeData> by lazy{
        MutableLiveData<TakingTimeData>()
    }

    // TODO ------------------------------- Дни недели ---------------------------------
    val allDayOfWeekNames = myRep.allDaysOfWeekNames

}

class MyDataModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyDataModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyDataModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}