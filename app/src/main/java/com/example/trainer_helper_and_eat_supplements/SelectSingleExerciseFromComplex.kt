package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.Adapters.SelectSingleExerciseForTrainAdapter
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.SelectSingleExerciseFromComplexActivityBinding

// Окно для выбора текущего выполняемого упражнения во время тренировки
class SelectSingleExerciseFromComplex : AppCompatActivity() {

    lateinit var selectedExercise:String
    lateinit var complexOfTrain:String
    lateinit var adapter: SelectSingleExerciseForTrainAdapter
    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // Для выбора элементов
    lateinit var binding: SelectSingleExerciseFromComplexActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заполнение объекта экрана
        binding = SelectSingleExerciseFromComplexActivityBinding.inflate(layoutInflater)

        // Кнопка возвращения на прошлое меню
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Получение аргументов
        var arguments = intent.extras

        // Получение выбранного упражнения и выбранного комплекса
        if(arguments != null){
            selectedExercise = arguments.getString(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN)!!
            complexOfTrain = arguments.getString(CONSTANTS.CHOOSEN_COMPLEX_FOR_TRAIN)!!
        }

        myDatamodel.getExercisesNamesByComplexName(complexOfTrain).observe(this){ exercises->
            adapter = SelectSingleExerciseForTrainAdapter(selectedExercise,
                exercises, myDatamodel, this)
            binding.exerciseList.layoutManager = LinearLayoutManager(this)
            binding.exerciseList.adapter = adapter
        }


        // Заголовок
        supportActionBar?.title = "Выберете упражнение"

        setContentView(binding.root)
    }

    // Добавляю кнопку подтверждения сохранения упражнения
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ok_menu, menu)
        return true
    }

    // Действия при нажатии на кнопки меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.ok_btn){
            returnResult()
        }
        // При нажатии кнопки возврата
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun returnResult(){
        var intent = Intent()
        var choosenExerciseName = adapter.nameOfSelectedItem
        intent.putExtra(CONSTANTS.CURRENT_EXERCISE_OF_TRAIN, choosenExerciseName)
        setResult(RESULT_OK, intent)
        finish()
    }
}