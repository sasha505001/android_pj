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
import com.example.trainer_helper_and_eat_supplements.Adapters.SelectSingleComplexAdapter
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.PrepareForTrainActivityBinding

class PrepareForTrainActivity : AppCompatActivity() {
    lateinit var adapter:SelectSingleComplexAdapter
    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // Для выбора элементов
    lateinit var binding:PrepareForTrainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заголовок
        supportActionBar?.title = "Тренировка"

        // Заполнение объекта экрана
        binding = PrepareForTrainActivityBinding.inflate(layoutInflater)

        // Список существующих комплексов
        myDatamodel.allComplexesNames.observe(this){
            adapter = SelectSingleComplexAdapter(it, myDatamodel, this)
            binding.complexesList.layoutManager = LinearLayoutManager(this)
            binding.complexesList.adapter = adapter
        }

        // Кнопка возвращения на прошлое меню
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)
    }

    // Добавляю кнопку подтверждения
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ok_menu, menu)
        return true
    }

    // Действия при нажатии на кнопки меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // При нажатии галочки

        if (item.itemId == R.id.ok_btn){
            returnResult()

        }

        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun returnResult(){
        var intent = Intent()
        var nameOfTrain = binding.nameOfTrain.text.toString()
        var choosenComplexName = adapter.nameOfSelectedItem

        Log.d("MyLog", nameOfTrain+"2")
        // Предупреждение
        var alertString:String = ""
        if(nameOfTrain == ""){
            alertString = alertString + "Введите название тренировки"
        }
        // Вывод предупреждающий строки
        if(alertString!=""){
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Невозможно начать тренировку")
            alertDialog.setMessage(alertString)
            alertDialog.setPositiveButton("Ок"){
                    dialog, id ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
        else{
            intent.putExtra(CONSTANTS.NAME_OF_TRAIN, nameOfTrain)
            intent.putExtra(CONSTANTS.CHOOSEN_COMPLEX_FOR_TRAIN, choosenComplexName)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}