package com.example.trainer_helper_and_eat_supplements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.trainer_helper_and_eat_supplements.Database.Data.TakingTimeData
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.TimeOfTakingEditAddActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.TimeOfTakingFoodAdditivesActivityBinding

class TimeOfTakingEditAdd : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    var lastFoodAdditive:TakingTimeData? = null

    // Для выбора элементов
    lateinit var binding: TimeOfTakingEditAddActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Заполнение объекта экрана
        binding = TimeOfTakingEditAddActivityBinding.inflate(layoutInflater)

        // Заголовок
        supportActionBar?.title = "Время приёма"


        // Кнопка возвращения на прошлое меню
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)
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

    // Добавляю кнопку подтверждения сохранения упражнения
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ok_menu, menu)
        return true
    }
}