package com.example.trainer_helper_and_eat_supplements

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.fonts.SystemFonts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.Adapters.Approach.ApproachAdapter
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.ActivityTrainBinding
import com.example.trainer_helper_and_eat_supplements.databinding.CustomTimePickerBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.math.roundToInt


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

    var timeOfTrain = 0.0


    // Массив данных о подходах
    //var dataOfTrain = mutableMapOf<String, ArrayList<HashMap<String, Float>>>()

    // Редактирование подхода упражнения
    val editApproach =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->


    }

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
    val addNewApproach = registerForActivityResult  (ActivityResultContracts.StartActivityForResult()){
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

    val editExistingApproach = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == RESULT_OK){
            // Узнаю название выбранного упражнения
            val intent = result.data

            // имя упражнения
            val nameOfExercise = intent!!.getStringExtra(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN)!!
            // порядковый номер
            val idOfApproach = intent!!.getIntExtra(CONSTANTS.ID_OF_APPROACH, 0)
            // Загружаемый массив
            val mesuaresAndValues = intent!!.getSerializableExtra(CONSTANTS.MEASURE_VALUE_OF_APPROACH) as HashMap<String, Float>

            myDatamodel.editSingleApproach(nameOfExercise, idOfApproach, mesuaresAndValues)
            setAdapter(nameOfCurrentExercise)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainBinding.inflate(layoutInflater)

        if(myDatamodel.myApproachesOfTrain.value==null){
            myDatamodel.myApproachesOfTrain.value =
                mutableMapOf<String, ArrayList<HashMap<String, Float>>>()
        }

        // Получение аргументов
        var arguments = intent.extras

        // Получаю информацию о тренировке
        if(arguments!=null){
            nameOfTrain = arguments.getString(CONSTANTS.NAME_OF_TRAIN)!!
            binding.nameOfTrainText.text = nameOfTrain
            nameOfTrainComplex = arguments.getString(CONSTANTS.CHOOSEN_COMPLEX_FOR_TRAIN)!!
        }
        binding.timeOfTrain.base = SystemClock.elapsedRealtime()
        binding.timeOfTrain.start()

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

        // Кнопка возврата
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Тренировка"
        setContentView(binding.root)
    }








    // Меню сверху при нажатии кнопок
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home){
            // TODO сделать предупрежающий алерт диалог
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Внимание!!!")
            builder.setMessage("Вы точно хотите завершить тренировку, " +
            "несохранённые данные будут удалены")
            builder.setNegativeButton("No"){ dialog, i ->
                dialog.dismiss()
            }
            builder.setPositiveButton("Yes"){ dialog, i ->
                dialog.dismiss()
                finish()
            }
            builder.show()
        }
        if(item.itemId == R.id.finish_btn){
            Toast.makeText(this, "Сохранение в бд", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.train_menu, menu)
        return true
    }

    // Ставлю список подходов исходя из того какое выбрано упражнение
    fun setAdapter(nameOfExercise:String){
        // Инициализирую адаптер
        myDatamodel.myApproachesOfTrain.observe(this){ dataOfTrain->
            curAdapter = ApproachAdapter(
                nameOfExercise, dataOfTrain.get(nameOfExercise)!!, myDatamodel, this,
            editExistingApproach)
            binding.listOfApproaches.layoutManager = LinearLayoutManager(this)
            binding.listOfApproaches.adapter = curAdapter
            curAdapter.notifyDataSetChanged()
        }
    }

    // Функция для изменения времени
    fun changeTimeInTrain(view: View){
        val alertBinding = CustomTimePickerBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(this).setView(alertBinding.root)
            .setTitle("Время отдыха")

        val alertDialog = builder.show()

        alertBinding.acceptBtn.setOnClickListener(){
            var alertStr = ""
            // минуты
            var minutesStr:String = alertBinding.editMinutes.text.toString()
            var minutesInt = minutesStr.toIntOrNull()
            if (minutesInt == null){
                alertStr = "Введите целое число в поле минут"
            }
            else if (minutesInt<0 || minutesInt > 60){
                alertStr = "Введите количество минут от 0 до 60"
            }
            // секунды
            var secondsStr:String = alertBinding.editSeconds.text.toString()
            var secondsInt = secondsStr.toIntOrNull()
            if(secondsInt == null){
                alertStr = "Введите целое число в поле секунд"
            }
            else if (secondsInt<0 || secondsInt > 60){
                alertStr = "Введите количество секунд от 0 до 60"
            }
            if(alertStr!= ""){
                Toast.makeText(applicationContext, alertStr, Toast.LENGTH_LONG).show()
            }
            else{

                if (minutesInt!!<10){
                    minutesStr = "0" + minutesStr
                }
                if(secondsInt!!<10){
                    secondsStr = "0" + secondsStr
                }
                millsOfTimer = ((minutesInt * 60 + secondsInt) * 1000).toLong()
                var result = minutesStr + ":" + secondsStr
                binding.timeOfRest.text = result
                timeOfRestStr = result
                myTimer?.cancel()
                binding.startRestTimerBtn.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                restTimerStarted = false
                alertDialog.dismiss()
            }
        }

        alertBinding.cancleBtn.setOnClickListener(){
            alertDialog.dismiss()
        }
    }

    // Получаю информацию о текущему упражнении
    fun getInfoFromCurrentExerciseOnClick(view: View){
        val intent = Intent(this, ObserverOfExerciseActivity::class.java)
        val nameOfExercise = binding.currentExercise.text.toString()
        intent.putExtra(CONSTANTS.NAMEOFOBSERVE, nameOfExercise)
        startActivity(intent)
    }




    // Выбираю упражнение из комплекса
    fun selectExerciseFromComplexOnClick(view: View){
        val intent = Intent(this, SelectSingleExerciseFromComplex::class.java)
        intent.putExtra(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN, nameOfCurrentExercise)
        intent.putExtra(CONSTANTS.CHOOSEN_COMPLEX_FOR_TRAIN, nameOfTrainComplex)
        selectSingleExericse.launch(intent)
    }

    // Добавляю новый подход
    fun addNewApproachOnClick(view: View){
        val intent = Intent(this, EditAddApproach::class.java)
        // Имя упражнения
        val curNameOfExercise = binding.currentExercise.text.toString()
        intent.putExtra(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN, curNameOfExercise)

        // номер подхода
        var idOfApproach = myDatamodel.myApproachesOfTrain.value!!.get(curNameOfExercise)!!.size
        intent.putExtra(CONSTANTS.ID_OF_APPROACH, idOfApproach)
        addNewApproach.launch(intent)
    }

    // Таймер отдыха
    var restTimerStarted = false
    var millsOfTimer:Long = 90000
    var timeOfRestStr = "01:30"
    var myTimer:CountDownTimer? = null

    // Запуск таймера отдыха
    fun startRestTimerOfTrain(view: View){
        myTimer?.cancel()
        myTimer = object : CountDownTimer(millsOfTimer, 1000){
            override fun onTick(p0: Long) {
                binding.timeOfRest.text = getTimeStringFromLong(p0)
            }

            override fun onFinish() {

            }
        }
        if (restTimerStarted){
            binding.startRestTimerBtn.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
            restTimerStarted = !restTimerStarted
            binding.timeOfRest.text = timeOfRestStr
            myTimer!!.cancel()
        }
        else{
            binding.startRestTimerBtn.setImageResource(R.drawable.ic_baseline_pause_circle_24)
            restTimerStarted = !restTimerStarted
            myTimer!!.start()
        }
    }


    private fun getTimeStringFromLong(time: Long): String
    {
        val resultInt = time.toInt()
        //Log.d("MyLog", resultInt.toString())
        val minutes = resultInt / 1000  / 60

        val seconds = resultInt / 1000  % 60

        return makeTimeString(minutes, seconds)
    }

    private fun makeTimeString(min: Int, sec: Int): String = String.format("%02d:%02d", min, sec)


}