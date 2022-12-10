package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.Adapters.EditAddApproachAdapter
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddApproachActivityBinding

class EditAddApproach : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    lateinit var adapter:EditAddApproachAdapter
    // Для выбора элементов
    lateinit var binding: EditAddApproachActivityBinding
    // Имя упражнения(нужно как и при редактировании так и при добавлении)
    lateinit var nameOfExercise:String

    lateinit var  listOfValuesOfMesures:HashMap<String, String>

    lateinit var valAndMesures:HashMap<String, Float>

    var idOfApproach = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заголовок
        supportActionBar?.title = "Подход"


        // Заполнение объекта экрана
        binding = EditAddApproachActivityBinding.inflate(layoutInflater)

        // Получение аргументов
        var arguments = intent.extras


        if(arguments != null){
            // получаю имя упражнения
            nameOfExercise = arguments.getString(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN)!!

            // При редактировании
            var curHash  = arguments.getSerializable(CONSTANTS.EDIT_APPROACH_VALUE)
            if (curHash != null){
                valAndMesures = curHash as HashMap<String, Float>
            }
            else{
                valAndMesures = hashMapOf<String, Float>()
            }

            // Получаю номер в списке
            idOfApproach = arguments.getInt(CONSTANTS.ID_OF_APPROACH)
        }

        myDatamodel.getMesuresFromExerciseName(nameOfExercise).observe(this){ mesures->
            if(valAndMesures.size == 0){
                mesures.forEach(){
                    valAndMesures.put(it, 0.0f)
                }
            }
            adapter = EditAddApproachAdapter(valAndMesures as HashMap<String, Float>
                /* = java.util.HashMap<kotlin.String, kotlin.Float> */, myDatamodel, this)
            binding.mesureValuesList.layoutManager = LinearLayoutManager(this)
            binding.mesureValuesList.adapter = adapter
        }

        // Кнопка возвращения на прошлое меню
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)
    }

    // Добавляю кнопку подтверждения добавления подхода
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ok_menu, menu)
        return true
    }

    // Действия при нажатии на кнопки меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // При нажатии галочки
        if (item.itemId == R.id.ok_btn){
            sendResult()
        }
        // При нажатии кнопки возврата
        if(item.itemId == android.R.id.home){
            setResult(RESULT_CANCELED)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun sendResult(){
        listOfValuesOfMesures = adapter.mesureValueList
        var alertStr:String = ""
        for((key, value ) in listOfValuesOfMesures){
            if(value == ""){
                alertStr =  "Заполните всю информацию о подходе"
            }
        }
        if(alertStr != ""){
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Невозможно создать подход для упражнения")
            alertDialog.setMessage(alertStr)
            alertDialog.setPositiveButton("Ок"){
                    dialog, id ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
        else{
            var dataFromApproach = hashMapOf<String, Float>()
            for((key, value) in listOfValuesOfMesures){
                dataFromApproach.put(key, value.toFloat())
            }
            val result = Intent()
            result.putExtra(CONSTANTS.MEASURE_VALUE_OF_APPROACH, dataFromApproach)
            result.putExtra(CONSTANTS.ID_OF_APPROACH, idOfApproach)
            result.putExtra(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN, nameOfExercise)
            setResult(RESULT_OK, result)
            finish()
        }


    }
}