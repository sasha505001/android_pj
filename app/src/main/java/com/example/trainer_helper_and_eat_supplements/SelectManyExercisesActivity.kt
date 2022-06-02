package com.example.trainer_helper_and_eat_supplements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.SelectItemAdapter.SelectManyExerciseAdapter
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddComplexActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.SelectManyExercisesActivityBinding

class SelectManyExercisesActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // Для выбора элементов
    lateinit var binding:SelectManyExercisesActivityBinding

    // При создании activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заполнение объекта экрана
        binding = SelectManyExercisesActivityBinding.inflate(layoutInflater)

        myDatamodel.allExercisesName.observe(this){ exercises ->

            val adapter:SelectManyExerciseAdapter = SelectManyExerciseAdapter(exercises)
            binding.exercisesSelect.layoutManager = LinearLayoutManager(this)
            binding.exercisesSelect.adapter = adapter

        }

        // Получение аргументов
        var arguments = intent.extras

        // Получение типа выбора и листа
        if(arguments != null){
            // TODO передавать имя комплекса при редактировании
        }

        // Кнопка возвращения на прошлое меню
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Заголовок
        supportActionBar?.title = "Выберете упражнения"

        //
        setContentView(binding.root)
    }
}