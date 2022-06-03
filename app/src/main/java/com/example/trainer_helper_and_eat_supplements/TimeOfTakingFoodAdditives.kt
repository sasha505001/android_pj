package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.SelectManyExercisesActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.TimeOfTakingFoodAdditivesActivityBinding

class TimeOfTakingFoodAdditives : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    var lastFoodAdditive:String? = null

    // Для выбора элементов
    lateinit var binding: TimeOfTakingFoodAdditivesActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Заполнение объекта экрана
        binding = TimeOfTakingFoodAdditivesActivityBinding.inflate(layoutInflater)

        // Заголовок
        supportActionBar?.title = "Все времена приёма"

        // Кнопка возвращения на прошлое меню
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)
    }

    // Добавляю кнопку подтверждения сохранения упражнения
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ok_menu, menu)
        return true
    }

    // Действия при нажатии на кнопки меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // При нажатии галочки
        if (item.itemId == R.id.ok_btn){
            Log.d("MyLog", "Ok")
            // TODO проверка и создание в бд
            //saveInDatabase()
        }
        // При нажатии кнопки возврата
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // Функция для нажатия добавления нового
    fun addNewTakingTime(view: View){
        val intent = Intent(this, TimeOfTakingEditAdd::class.java)
        editAddNewTakingTime.launch(intent)
    }


    // TODO получить результирующую строку
    val editAddNewTakingTime = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val exerciseString = result.data?.getStringExtra(CONSTANTS.SELECT_OF_MANY_EXERCISES)
            if(exerciseString != ""){
                // binding.exercisesText.setText(exerciseString)
            }
        }
    }


}