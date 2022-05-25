package com.example.trainer_helper_and_eat_supplements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.MainList.MainListFragment
import com.example.trainer_helper_and_eat_supplements.databinding.MainListActivityBinding

class MainActivityList : AppCompatActivity() {

    // объект для обращения к элементам экрана
    lateinit var binding: MainListActivityBinding

    // Меню
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    // текущий экран
    var currentList:CONSTANTS.NavMenuBtns = CONSTANTS.NavMenuBtns.TRAINING_STORY

    // база данных
    var database: MyDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заполнение объекта экрана
        binding = MainListActivityBinding.inflate(layoutInflater)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.mainDrawerLayout, R.string.nav_open, R.string.nav_close)
        binding.mainDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // слушатель для навигационного меню
        binding.navMenu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.training_story -> {
                    setListFragment(CONSTANTS.NavMenuBtns.TRAINING_STORY)
                }
                R.id.exercises -> {
                    setListFragment(CONSTANTS.NavMenuBtns.EXERCISES)
                }
                R.id.complexes -> {
                    setListFragment(CONSTANTS.NavMenuBtns.COMPLEXES)
                }
                R.id.food_additives -> {
                    setListFragment(CONSTANTS.NavMenuBtns.FOOD_ADDITIVES)
                }
            }
            true
        }

        // Запуск базы данных
        Thread{
            database =  MyDatabase.getDatabase(this)
        }.start()

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListFragment(typeOfList: CONSTANTS.NavMenuBtns){
        currentList = typeOfList
        var title = when(typeOfList){
            CONSTANTS.NavMenuBtns.COMPLEXES -> getResources().getString(R.string.complexes)
            CONSTANTS.NavMenuBtns.EXERCISES -> getResources().getString(R.string.exercises)
            CONSTANTS.NavMenuBtns.FOOD_ADDITIVES -> getResources().getString(R.string.food_additives)
            CONSTANTS.NavMenuBtns.TRAINING_STORY -> getResources().getString(R.string.training_story)
        }
        supportActionBar?.title = title
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
        supportFragmentManager.beginTransaction().replace(
            R.id.main_frame,
            MainListFragment.newInstance(typeOfList, database)
        ).commit()
        binding.mainDrawerLayout.closeDrawers()
    }
}