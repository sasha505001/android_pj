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
import com.example.trainer_helper_and_eat_supplements.databinding.ActivityTrainBinding


class TrainActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    lateinit var binding: ActivityTrainBinding
    lateinit var nameOfTrainComplex:String
    lateinit var nameOfTrain:String
    lateinit var nameOfCurrentExercise:String

    // Выбор текущего упражнения
    val selectSingleExericse =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
            if(result.resultCode == RESULT_OK){
                // Узнаю название выбранного упражнения
                val intent = result.data
                val nameOfExercise = intent!!.getStringExtra(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN)
                nameOfCurrentExercise = nameOfExercise!!
                binding.currentExercise.text = nameOfExercise
            }

    }

    // Добавления подхода упражнения
    val addNewApproach = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == RESULT_OK){
            // Получаю массив значений подхода
            val result = result.data
            var listMeasureValue =
                result!!.getSerializableExtra(CONSTANTS.MEASURE_VALUE_OF_APPROACH)
                        as HashMap<String, String>

            for((key, value) in listMeasureValue ){
                Log.d("MyLog", key + ": " + value)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainBinding.inflate(layoutInflater)

        // Получение аргументов
        var arguments = intent.extras
        // Получаю информацию о тренировке
        if(arguments!=null){
            nameOfTrain = arguments.getString(CONSTANTS.NAME_OF_TRAIN)!!
            binding.nameOfTrainText.text = nameOfTrain
            nameOfTrainComplex = arguments.getString(CONSTANTS.CHOOSEN_COMPLEX_FOR_TRAIN)!!
        }

        // Выбор текущего упражнения
        binding.selectFromList.setOnClickListener(){
            val intent = Intent(this, SelectSingleExerciseFromComplex::class.java)
            intent.putExtra(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN, nameOfCurrentExercise)
            intent.putExtra(CONSTANTS.CHOOSEN_COMPLEX_FOR_TRAIN, nameOfTrainComplex)
            selectSingleExericse.launch(intent)
        }

        // Информация об упражнении
        binding.infoBtnExercise.setOnClickListener(){
            val intent = Intent(this, ObserverOfExerciseActivity::class.java)
            val nameOfExercise = binding.currentExercise.text.toString()
            intent.putExtra(CONSTANTS.NAMEOFOBSERVE, nameOfExercise)
            startActivity(intent)
        }

        // Добавление нового подхода
        binding.addNewApproach.setOnClickListener(){
            val intent = Intent(this, EditAddApproach::class.java)
            val curNameOfExercise = binding.currentExercise.text.toString()
            intent.putExtra(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN, curNameOfExercise)
            addNewApproach.launch(intent)
        }

        // Выбираю текущее упражнение комплекса
        myDatamodel.getExercisesNamesByComplexName(nameOfTrainComplex).observe(this){
            nameOfCurrentExercise = it.get(0)
            binding.currentExercise.text = nameOfCurrentExercise
        }

        // Кнопка возврата
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Тренировка"
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            // TODO сделать предупрежающий алерт диалог
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.train_menu, menu)
        return true
    }

    fun onAddApproachClicked(view: View){
        val myIntent = Intent(this, EditAddApproach::class.java)
        startActivity(myIntent)
    }
}