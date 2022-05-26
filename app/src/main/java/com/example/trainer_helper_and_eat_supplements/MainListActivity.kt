package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListComplexAdapter
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListExerciseAdapter
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListFoodAdditiveAdapter
import com.example.trainer_helper_and_eat_supplements.MainList.ListAdapters.MainListTrainsAdapter
import com.example.trainer_helper_and_eat_supplements.databinding.MainListActivityBinding

class MainListActivity : AppCompatActivity() {



    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // Списки для загрузки в адаптер
    var complexList:List<String>? = null
    var exerciseList:List<String>? = listOf("exercise")
    var foodAdditiveList:List<String>? = listOf("food additive")
    var trainList:List<String>? = listOf("train")

    // объект для обращения к элементам экрана
    lateinit var binding: MainListActivityBinding

    // Меню
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    // текущий экран
    lateinit var currentList:CONSTANTS.NavMenuBtns



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // заполнение списков
        //exerciseList = myDatamodel.allExercisesName.value


        myDatamodel.allExercisesName.observe(this){
            exerciseList = it
        }

        // Заполнение объекта экрана
        binding = MainListActivityBinding.inflate(layoutInflater)

        // Ставлю первый лист TODO livedata должна
        currentList = CONSTANTS.NavMenuBtns.TRAINING_STORY

        // Меню
        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.mainDrawerLayout, R.string.nav_open, R.string.nav_close)
        binding.mainDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // слушатель для навигационного меню
        binding.navMenu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.training_story -> {
                    currentList = CONSTANTS.NavMenuBtns.TRAINING_STORY
                    var adapter = MainListTrainsAdapter(trainList)
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        MainListFragment.newInstance(this, currentList, adapter)
                    ).commit()
                }
                R.id.exercises -> {
                    currentList = CONSTANTS.NavMenuBtns.EXERCISES
                    var adapter = MainListExerciseAdapter(exerciseList)
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        MainListFragment.newInstance(this, currentList, adapter)
                    ).commit()
                }
                R.id.complexes -> {
                    currentList = CONSTANTS.NavMenuBtns.COMPLEXES
                    var adapter = MainListComplexAdapter(complexList)
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        MainListFragment.newInstance(this, currentList, adapter)
                    ).commit()
                }
                R.id.food_additives -> {
                    currentList = CONSTANTS.NavMenuBtns.FOOD_ADDITIVES
                    var adapter = MainListFoodAdditiveAdapter(foodAdditiveList)
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        MainListFragment.newInstance(this, currentList, adapter)
                    ).commit()
                }
            }
            supportActionBar?.title = getStringTitle(currentList)
            binding.mainDrawerLayout.closeDrawers()
            true
        }


        //setListFragment(currentList)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    // Activity for result
    val editAddExercise = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == RESULT_OK){

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_btn){
            var intent: Intent = when(currentList){
                CONSTANTS.NavMenuBtns.COMPLEXES->{
                    Intent(this, EditAddComplexActivity::class.java)
                }
                CONSTANTS.NavMenuBtns.EXERCISES->{
                    Intent(this, EditAddExerciseActivity::class.java)
                }
                CONSTANTS.NavMenuBtns.TRAINING_STORY->{
                    Intent(this, EditTrainActivity::class.java)
                }
                CONSTANTS.NavMenuBtns.FOOD_ADDITIVES->{
                    Intent(this, EditAddFoodAdditiveActivity::class.java)
                }
            }
            startActivity(intent)

        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    // Получение текущего листа
    fun getStringTitle(typeOfList: CONSTANTS.NavMenuBtns):String{
        var str = when(typeOfList){
            CONSTANTS.NavMenuBtns.COMPLEXES -> getResources().getString(R.string.complexes)
            CONSTANTS.NavMenuBtns.EXERCISES -> getResources().getString(R.string.exercises)
            CONSTANTS.NavMenuBtns.FOOD_ADDITIVES -> getResources().getString(R.string.food_additives)
            CONSTANTS.NavMenuBtns.TRAINING_STORY -> getResources().getString(R.string.training_story)
        }
        return str
    }
}

