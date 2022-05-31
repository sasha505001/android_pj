package com.example.trainer_helper_and_eat_supplements

import androidx.lifecycle.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExerciseMeasuresData
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.Data.MeasuresData
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

    // Добавление упражнения полностью со всеми связями
    fun addFullyExercise(exerciseData: ExercisesData, measureNames:List<String>) =
        viewModelScope.launch {
            myRep.addFullyExercise(exerciseData, measureNames)
        }

    // Удаление упражнения(без связей)
    fun deleteExerciseByName(exerciseName:String) = viewModelScope.launch {
        myRep.deleteExerciseByName(exerciseName)
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

    // TODO --------------------------------------- Мера - упражнение -----------------------------
    // Добавление
    fun insertExerciseMeasure(data:ExerciseMeasuresData)= viewModelScope.launch(){
        myRep.insertMesureExercise(data)
    }

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