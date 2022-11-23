package com.example.trainer_helper_and_eat_supplements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.Adapters.ObserverOfComplexAllExercisesAdapter
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.ObserverOfComplexActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.ObserverOfExerciseActivityBinding

// Окно отображения Комплекса
class ObserverOfComplexActivity : AppCompatActivity() {

    // Для выбора элементов
    lateinit var binding: ObserverOfComplexActivityBinding


    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Наполняю для редактирования
        binding = ObserverOfComplexActivityBinding.inflate(layoutInflater)

        // Получение аргументов для отображения
        var arguments = intent.extras

        if (arguments!=null){
            val nameOfComplex = arguments.getString(CONSTANTS.NAMEOFOBSERVE)

            binding.complexNameText.text = nameOfComplex
            supportActionBar?.title = "Комплекс"
            myDatamodel.getExercisesNamesByComplexName(nameOfComplex!!).observe(this){ exerciseNames->

                val adapter = ObserverOfComplexAllExercisesAdapter(exerciseNames, myDatamodel, this)
                binding.exerciseList.layoutManager = LinearLayoutManager(this)
                binding.exerciseList.adapter = adapter

            }

        }

        // Кнопка возврата
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)
    }
    // Добавляю кнопку подтверждения сохранения упражнения
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}