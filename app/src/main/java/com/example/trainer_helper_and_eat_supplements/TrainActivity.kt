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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.Adapters.Approach.ApproachAdapter
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.ActivityTrainBinding


class TrainActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // Для выбора элементов
    lateinit var binding: ActivityTrainBinding
    // Имя комплекса тренировки
    lateinit var nameOfTrainComplex:String
    // Название тренировки
    lateinit var nameOfTrain:String
    // Название текущего упражнения
    lateinit var nameOfCurrentExercise:String

    // Адаптер для отображения подходов упражнения
    lateinit var curAdapter: ApproachAdapter


    // Массив данных о подходах
    //var dataOfTrain = mutableMapOf<String, ArrayList<HashMap<String, Float>>>()

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
                setAdapter(nameOfCurrentExercise)
            }

    }

    // Добавления подхода упражнения
    val addNewApproach = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == RESULT_OK){
            // Получаю массив значений подхода
            val result = result.data
            var dataOfApproach =
                result!!.getSerializableExtra(CONSTANTS.MEASURE_VALUE_OF_APPROACH)
                        as HashMap<String, Float>
            myDatamodel.myApproachesOfTrain.value!!.get(nameOfCurrentExercise)!!.add(dataOfApproach)
            setAdapter(nameOfCurrentExercise)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainBinding.inflate(layoutInflater)
        myDatamodel.myApproachesOfTrain.value = mutableMapOf<String, ArrayList<HashMap<String, Float>>>()
        // Получение аргументов
        var arguments = intent.extras
        // Получаю информацию о тренировке
        if(arguments!=null){
            nameOfTrain = arguments.getString(CONSTANTS.NAME_OF_TRAIN)!!
            binding.nameOfTrainText.text = nameOfTrain
            nameOfTrainComplex = arguments.getString(CONSTANTS.CHOOSEN_COMPLEX_FOR_TRAIN)!!
        }

        // Выбираю текущее упражнение комплекса
        myDatamodel.getExercisesNamesByComplexName(nameOfTrainComplex).observe(this){ exercises->
            exercises.forEach(){ singleExercise->
                myDatamodel.myApproachesOfTrain.value!!.put(singleExercise, arrayListOf<HashMap<String, Float>>())
            }
            nameOfCurrentExercise = exercises.get(0)
            binding.currentExercise.text = nameOfCurrentExercise

            myDatamodel.myApproachesOfTrain
            setAdapter(nameOfCurrentExercise)
            myDatamodel.myApproachesOfTrain.observe(this){
                setAdapter(nameOfCurrentExercise)
            }
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

    fun setAdapter(nameOfExercise:String){
        // Инициализирую адаптер
        myDatamodel.myApproachesOfTrain.observe(this){ dataOfTrain->
            curAdapter = ApproachAdapter(dataOfTrain.get(nameOfExercise)!!, myDatamodel, this)
            binding.listOfApproaches.layoutManager = LinearLayoutManager(this)
            binding.listOfApproaches.adapter = curAdapter
            //curAdapter.notifyDataSetChanged()
        }
    }

}