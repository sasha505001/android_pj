package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.Adapters.TimeOfTakingFoodAdapter
import com.example.trainer_helper_and_eat_supplements.Database.Data.TakingTimeData
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.SingleEditAddTakingTimeBinding
import com.example.trainer_helper_and_eat_supplements.databinding.TimeOfTakingFoodAdditivesActivityBinding
import java.text.SimpleDateFormat
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
                this,
                copyOfMutableList
            )
        }

        myDatamodel.editTime.value = null

        myDatamodel.editTime.observe(this){
            if(it!=null){
                addEditTakingTimeData(it)
            }

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
        addEditTakingTimeData(null)
    }

    fun addEditTakingTimeData(editData:TakingTimeData?){
        val dialog = AlertDialog.Builder(this)
        var alertBinding = SingleEditAddTakingTimeBinding.inflate(layoutInflater)



        alertBinding.timePicker.setIs24HourView(true)
        val cal = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("hh:mm")
        //alertBinding.timePicker.currentHour = cal.time.hours
        //alertBinding.timePicker.currentMinute = cal.time.minutes

        if (editData!=null){
            alertBinding.timePicker.currentMinute = editData.taking_time.minutes
            alertBinding.timePicker.currentHour = editData.taking_time.hours
            alertBinding.countEditText.setText(editData.dose_taken.toString())
        }

        dialog.setPositiveButton("Ок")
        { dialog, curI ->
            var alertStr:String = ""
            val gotCountString = alertBinding.countEditText.text.toString()
            if(gotCountString == ""){
                alertStr = "Введите дозу"
            }

            cal.set(Calendar.HOUR_OF_DAY, alertBinding.timePicker.currentHour)
            cal.set(Calendar.MINUTE, alertBinding.timePicker.currentMinute)
            val gotTimeString =
                timeFormat.format(cal.time)

            copyOfMutableList.forEach(){
                val curTimeString = timeFormat.format(it.taking_time.time)
                if(curTimeString == gotTimeString && editData == null){
                    alertStr = "Время принятия уже существует"
                }
            }
            if(alertStr==""){
                if(editData!=null){
                    copyOfMutableList.removeAt(editData.id)
                    myDatamodel.allTakingTimeForFoodAdditive.value = copyOfMutableList
                }
                val data = TakingTimeData(
                    gotCountString.toFloat(),
                    cal.time
                )
                copyOfMutableList.add(data)
                myDatamodel.allTakingTimeForFoodAdditive.value = copyOfMutableList
                dialog.dismiss()
            }
            else{
                Toast.makeText(this, alertStr, Toast.LENGTH_SHORT).show()
            }
        }
        dialog.setNegativeButton("Отмена"){ dialog, curI ->
            dialog.dismiss()
        }
        dialog.setView(alertBinding.root)

        dialog.show()
    }


    // TODO получить результирующую строку
    val editAddNewTakingTime:ActivityResultLauncher<Intent> = registerForActivityResult(
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