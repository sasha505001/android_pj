package com.example.trainer_helper_and_eat_supplements

import android.os.Message
import androidx.lifecycle.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExerciseMeasuresData
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.Data.FoodAdditiveMesureData
import com.example.trainer_helper_and_eat_supplements.Database.Data.MeasuresData
import com.example.trainer_helper_and_eat_supplements.LiveData.MyRepository
import kotlinx.coroutines.launch


class MyDataModel(private val myRep: MyRepository): ViewModel(){

    // TODO сделать чтобы вид текущего листа запоминался

    // -----------------------------  Упражнения  --------------------------------------------
    val allExercisesData: LiveData<List<ExercisesData>> = myRep.allExercises
    val allExercisesName: LiveData<List<String>> = myRep.allExerciseName
    fun insertExercise(exercisesData: ExercisesData) = viewModelScope.launch(){
        myRep.insertExercise(exercisesData)
    }
    fun deleteExerciseByName(exerciseName:String) = viewModelScope.launch {
        myRep.deleteExerciseByName(exerciseName)
    }

    fun getExerciseByName(exerciseName: String):LiveData<ExercisesData>{
        val result = MutableLiveData<ExercisesData>()
        viewModelScope.launch {
            val returnRepo = myRep.getExerciseByName(exerciseName)
            result.postValue(returnRepo)
        }
        return result
    }

    // --------------------------------- Меры ----------------------------------------------
    val allMesures:LiveData<List<MeasuresData>> = myRep.allMesureData
    val allMesuresName: LiveData<List<String>> = myRep.allMesuresName

    fun getMesureByName(name:String): LiveData<MeasuresData>{
        val result = MutableLiveData<MeasuresData>()
        viewModelScope.launch {
            val returnRepo = myRep.getMesureByName(name)
            result.postValue(returnRepo)
        }
        return result
    }

    // Мера - упражнение
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