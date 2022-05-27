package com.example.trainer_helper_and_eat_supplements

import androidx.lifecycle.*
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import com.example.trainer_helper_and_eat_supplements.LiveData.MyRepository
import kotlinx.coroutines.launch


open class MyDataModel(private val myRep: MyRepository): ViewModel(){


    val curList:MutableLiveData<CONSTANTS.NavMenuBtns> by lazy {
        MutableLiveData<CONSTANTS.NavMenuBtns>()
    }

    val allExercisesData: LiveData<List<ExercisesData>> = myRep.allExercises
    val allExercisesName: LiveData<List<String>> = myRep.allExerciseName
    val allMesuresName: LiveData<List<String>> = myRep.allMesuresName

    fun insertExercise(exercisesData: ExercisesData) = viewModelScope.launch(){
        myRep.insertExercise(exercisesData)
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