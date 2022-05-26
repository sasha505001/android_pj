package com.example.trainer_helper_and_eat_supplements

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApp
import kotlinx.coroutines.launch


class MyDataModel(var myApp: MyApp): ViewModel(){

    val allExercisesData: LiveData<List<ExercisesData>>? = myApp.allExercises
    val allExercisesName: LiveData<List<String>>? = myApp.allExerciseName

    fun insertExercise(exercisesData: ExercisesData) = viewModelScope.launch(){
        myApp.insertExercise(exercisesData)
    }
}

class MyDataModelFactory(private val myApp: MyApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyDataModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyDataModel(myApp) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}