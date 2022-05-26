package com.example.trainer_helper_and_eat_supplements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.*
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListComplexAdapter
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListExerciseAdapter
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListFoodAdditiveAdapter
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListTrainsAdapter
import com.example.trainer_helper_and_eat_supplements.databinding.MainListFragmentBinding


class MainListFragment() : Fragment() {

    // Адаптеры
    var trainAdapter:MainListTrainsAdapter? = null
    var exerciseAdapter:MainListExerciseAdapter? = null
    var complexAdapter:MainListComplexAdapter? = null
    var foodAdditiveAdapter:MainListFoodAdditiveAdapter? = null

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
        binding = MainListFragmentBinding.inflate(inflater)
        binding.fragmentRecyclerView.layoutManager = LinearLayoutManager(this.activity)
        when(typeOfFragment){
            CONSTANTS.NavMenuBtns.COMPLEXES ->{
                binding.fragmentRecyclerView.adapter = complexAdapter
            }
            CONSTANTS.NavMenuBtns.EXERCISES ->{
                binding.fragmentRecyclerView.adapter = exerciseAdapter
            }
            CONSTANTS.NavMenuBtns.TRAINING_STORY ->{
                binding.fragmentRecyclerView.adapter = trainAdapter
            }
            CONSTANTS.NavMenuBtns.FOOD_ADDITIVES ->{
                binding.fragmentRecyclerView.adapter = foodAdditiveAdapter
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(
            typeOfList:CONSTANTS.NavMenuBtns,
            adapter: MainListTrainsAdapter
        ):Fragment{
            var myFragment = MainListFragment()
            myFragment.typeOfFragment = typeOfList
            myFragment.trainAdapter = adapter
            return myFragment
        }
        @JvmStatic
        fun newInstance(
            typeOfList:CONSTANTS.NavMenuBtns,
            adapter: MainListExerciseAdapter
        ):Fragment{
            var myFragment = MainListFragment()
            myFragment.typeOfFragment = typeOfList
            myFragment.exerciseAdapter = adapter
            return myFragment
        }
        @JvmStatic
        fun newInstance(
            typeOfList:CONSTANTS.NavMenuBtns,
            adapter: MainListComplexAdapter
        ):Fragment{
            var myFragment = MainListFragment()
            myFragment.typeOfFragment = typeOfList
            myFragment.complexAdapter = adapter
            return myFragment
        }
        @JvmStatic
        fun newInstance(
            typeOfList:CONSTANTS.NavMenuBtns,
            adapter: MainListFoodAdditiveAdapter
        ):Fragment{
            var myFragment = MainListFragment()
            myFragment.typeOfFragment = typeOfList
            myFragment.foodAdditiveAdapter = adapter
            return myFragment
        }
    }
}