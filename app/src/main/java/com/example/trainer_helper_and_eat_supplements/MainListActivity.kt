package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.Adapters.MainList.MainListComplexAdapter
import com.example.trainer_helper_and_eat_supplements.Adapters.MainList.MainListExerciseAdapter
import com.example.trainer_helper_and_eat_supplements.Adapters.MainList.MainListFoodAdditiveAdapter
import com.example.trainer_helper_and_eat_supplements.databinding.MainListActivityBinding

// TODO ошибка при повороте экрана, ошибка при возвращение на это activity
class MainListActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // объект для обращения к элементам экрана
    lateinit var binding: MainListActivityBinding

    // Меню
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    // текущий экран
    var currentList:CONSTANTS.NavMenuBtns = CONSTANTS.NavMenuBtns.COMPLEXES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заполнение объекта экрана
        binding = MainListActivityBinding.inflate(layoutInflater)
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
                    setCurrentFragment()
                }
                R.id.exercises -> {
                    currentList = CONSTANTS.NavMenuBtns.EXERCISES
                    setCurrentFragment()
                }
                R.id.complexes -> {
                    currentList = CONSTANTS.NavMenuBtns.COMPLEXES
                    setCurrentFragment()

                }
                R.id.food_additives -> {
                    currentList = CONSTANTS.NavMenuBtns.FOOD_ADDITIVES
                    setCurrentFragment()
                }
            }

            binding.mainDrawerLayout.closeDrawers()
            true
        }

        // TODO начинаю с определенного листа
        currentList = CONSTANTS.NavMenuBtns.FOOD_ADDITIVES
        // Обзервер для текущего листа
        /*
        myDatamodel.curList.observe(this){
            currentList = it
            setCurrentFragment()
        }
*/

        setCurrentFragment()
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
            when(currentList){ // TODO сделать добавления для каждого
                CONSTANTS.NavMenuBtns.COMPLEXES->{
                    val curIntent =Intent(this, EditAddComplexActivity::class.java)
                    startActivity(curIntent)
                }
                CONSTANTS.NavMenuBtns.EXERCISES->{
                    val curIntent = Intent(this, EditAddExerciseActivity::class.java)
                    startActivity(curIntent)
                }
                CONSTANTS.NavMenuBtns.TRAINING_STORY->{
                    Intent(this, EditTrainActivity::class.java)
                }
                CONSTANTS.NavMenuBtns.FOOD_ADDITIVES->{
                    val curIntent = Intent(this, EditAddFoodAdditiveActivity::class.java)
                    startActivity(curIntent)
                }
            }
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

    fun setCurrentFragment(){
        when(currentList) {
            CONSTANTS.NavMenuBtns.COMPLEXES -> {
                myDatamodel.allComplexesNames.observe(this){
                    val adapter = MainListComplexAdapter(it, myDatamodel, this)
                    supportActionBar?.title = getStringTitle(currentList)
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        MainListFragment.newInstance(adapter, currentList)
                    ).commit()
                }
            }
            CONSTANTS.NavMenuBtns.EXERCISES -> {
                myDatamodel.allExercisesName.observe(this){
                    val adapter = MainListExerciseAdapter(it, myDatamodel, this)
                    supportActionBar?.title = getStringTitle(currentList)
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        MainListFragment.newInstance(adapter, currentList)
                    ).commit()
                }
            }
            CONSTANTS.NavMenuBtns.TRAINING_STORY -> {

            }
            CONSTANTS.NavMenuBtns.FOOD_ADDITIVES -> {
                myDatamodel.allFoodAdditiveNames.observe(this){
                    val adapter = MainListFoodAdditiveAdapter(it, myDatamodel, this)
                    supportActionBar?.title = getStringTitle(currentList)
                    supportFragmentManager.beginTransaction().replace(
                        R.id.main_frame,
                        MainListFragment.newInstance(adapter, currentList)
                    ).commit()
                }
            }
        }
    }


}

