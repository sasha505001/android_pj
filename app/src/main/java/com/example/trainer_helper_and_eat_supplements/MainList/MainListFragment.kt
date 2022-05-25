package com.example.trainer_helper_and_eat_supplements.MainList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trainer_helper_and_eat_supplements.CONSTANTS
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.R



class MainListFragment : Fragment() {

    // Тип списка который используется
    var typeOfFragment: CONSTANTS.NavMenuBtns? = null

    // База данных
    var myDatabase: MyDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_list_fragment, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(typeOfList: CONSTANTS.NavMenuBtns, database: MyDatabase?):Fragment{
            var myFragment = MainListFragment()
            myFragment.typeOfFragment = typeOfList
            myFragment.myDatabase = database
            return myFragment
        }
    }
}