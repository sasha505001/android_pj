package com.example.trainer_helper_and_eat_supplements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.Adapters.MainList.MainListComplexAdapter
import com.example.trainer_helper_and_eat_supplements.Adapters.MainList.MainListExerciseAdapter
import com.example.trainer_helper_and_eat_supplements.Adapters.MainList.MainListFoodAdditiveAdapter
import com.example.trainer_helper_and_eat_supplements.Adapters.MainList.MainListTrainsAdapter
import com.example.trainer_helper_and_eat_supplements.databinding.MainListFragmentBinding


class MainListFragment : Fragment() {
    // Адаптеры
    var complexAdapter: MainListComplexAdapter? = null
    var exerciseAdapter: MainListExerciseAdapter? = null
    var foodAdditiveAdapter: MainListFoodAdditiveAdapter? = null
    var trainsAdapter: MainListTrainsAdapter? = null

    // Тип списка который используется
    var typeOfFragment: CONSTANTS.NavMenuBtns = CONSTANTS.NavMenuBtns.EXERCISES

    // Раздувка
    lateinit var binding: MainListFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        // Заполнение фрагмента
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
                binding.fragmentRecyclerView.adapter = trainsAdapter
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
            adapter: MainListComplexAdapter,
            typeOfList:CONSTANTS.NavMenuBtns,
        ):Fragment{
            var myFragment = MainListFragment()
            myFragment.complexAdapter = adapter
            myFragment.typeOfFragment = typeOfList
            return myFragment
        }
        @JvmStatic
        fun newInstance(
            adapter: MainListExerciseAdapter,
            typeOfList:CONSTANTS.NavMenuBtns,
        ):Fragment{
            var myFragment = MainListFragment()
            myFragment.exerciseAdapter = adapter
            myFragment.typeOfFragment = typeOfList
            return myFragment
        }
        @JvmStatic
        fun newInstance(
            adapter: MainListFoodAdditiveAdapter,
            typeOfList:CONSTANTS.NavMenuBtns,
        ):Fragment{
            var myFragment = MainListFragment()
            myFragment.foodAdditiveAdapter = adapter
            myFragment.typeOfFragment = typeOfList
            return myFragment
        }
        @JvmStatic
        fun newInstance(
            adapter: MainListTrainsAdapter,
            typeOfList:CONSTANTS.NavMenuBtns,
        ):Fragment{
            var myFragment = MainListFragment()
            myFragment.trainsAdapter = adapter
            myFragment.typeOfFragment = typeOfList
            return myFragment
        }

    }
}