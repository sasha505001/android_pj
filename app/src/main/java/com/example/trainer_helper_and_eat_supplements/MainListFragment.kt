package com.example.trainer_helper_and_eat_supplements

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.*
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListComplexAdapter
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListExerciseAdapter
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListFoodAdditiveAdapter
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListTrainsAdapter
import com.example.trainer_helper_and_eat_supplements.databinding.MainListFragmentBinding


class MainListFragment(context: Context) : Fragment() {
    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(context)).myRep)
    }

    // Тип списка который используется
    var typeOfFragment: CONSTANTS.NavMenuBtns? = null

    // Раздувка
    lateinit var binding: MainListFragmentBinding

    // База данных
    var myDatabase: MyDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = MainListFragmentBinding.inflate(inflater)
        binding.fragmentRecyclerView.layoutManager = LinearLayoutManager(this.activity)
        when(typeOfFragment){
            CONSTANTS.NavMenuBtns.COMPLEXES ->{
                //binding.fragmentRecyclerView.adapter = MainListComplexAdapter(myDatamodel)
            }
            CONSTANTS.NavMenuBtns.EXERCISES ->{
                binding.fragmentRecyclerView.adapter = MainListExerciseAdapter(myDatamodel.allExercisesName.value)
            }
            CONSTANTS.NavMenuBtns.TRAINING_STORY ->{
                //binding.fragmentRecyclerView.adapter = MainListTrainsAdapter(myDatamodel)
            }
            CONSTANTS.NavMenuBtns.FOOD_ADDITIVES ->{
                //binding.fragmentRecyclerView.adapter = MainListFoodAdditiveAdapter(myDatamodel)
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(
            context: Context,
            typeOfList:CONSTANTS.NavMenuBtns,
        ):Fragment{
            var myFragment = MainListFragment(context)
            myFragment.typeOfFragment = CONSTANTS.NavMenuBtns.EXERCISES// TODO Для дебага
            return myFragment
        }
    }
}