package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.Adapters.TimeOfTakingFoodAdapter
import com.example.trainer_helper_and_eat_supplements.Database.Data.TakingTimeData
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.SelectManyExercisesActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.TimeOfTakingFoodAdditivesActivityBinding
import java.util.*

class TimeOfTakingFoodAdditives : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    var lastFoodAdditive:String? = null

    var copyOfMutableList:MutableList<TakingTimeData> = mutableListOf<TakingTimeData>()

    // Для выбора элементов
    lateinit var binding: TimeOfTakingFoodAdditivesActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Заполнение объекта экрана
        binding = TimeOfTakingFoodAdditivesActivityBinding.inflate(layoutInflater)

        // Заголовок
        supportActionBar?.title = "Все времена приёма"


        binding.TimeOfTakingrecyclerView.layoutManager = LinearLayoutManager(this)

        myDatamodel.allTakingTimeForFoodAdditive.observe(this){
            binding.TimeOfTakingrecyclerView.adapter = TimeOfTakingFoodAdapter(
                it,
                myDatamodel,
                this
            )
        }


        myDatamodel.allTakingTimeForFoodAdditive.value = copyOfMutableList
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
            val timeOfTakingString = result.data?.getStringExtra(CONSTANTS.RESULT_EDIT_ADD_TAKING_TIME)


            val myRes = timeOfTakingString!!.split(" - ")
            val hoursAndMin = myRes[0].split(":")
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hoursAndMin[0].toInt())
            cal.set(Calendar.MINUTE, hoursAndMin[1].toInt())
            val resultData = TakingTimeData(
                myRes[1].toFloat(),
                cal.time
            )
            copyOfMutableList.add(resultData)
            myDatamodel.allTakingTimeForFoodAdditive.value = copyOfMutableList
        }
    }


}