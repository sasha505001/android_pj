package com.example.trainer_helper_and_eat_supplements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddComplexActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddFoodAdditiveActivityBinding

class EditAddFoodAdditiveActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // Для выбора элементов
    lateinit var binding: EditAddFoodAdditiveActivityBinding

    // Имя объекта(только при редактировании)
    var nameOfEditObject:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заголовок
        supportActionBar?.title = "Пищ. добавка"

        // Заполнение объекта экрана
        binding = EditAddFoodAdditiveActivityBinding.inflate(layoutInflater)

        // Получение аргументов при редактировании
        var arguments = intent.extras

        if(arguments != null){
            nameOfEditObject = arguments.getString(CONSTANTS.NAMEOFEDITOBJ)
        }

        myDatamodel.allFoodAdditiveMeasureNames.observe(this){ mesuresNames ->
            val mesureSpinAdapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mesuresNames)
            mesureSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.mesureFoodAdditiveSpinner.adapter = mesureSpinAdapter
        }
        //

        myDatamodel.allScheduleNames.observe(this){ scheduleNames ->

            val mesureSpinAdapter =
                ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, scheduleNames)
            mesureSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.scheduleSpinner.adapter = mesureSpinAdapter
        }

        setContentView(binding.root)
    }

    // При нажатии на время
    fun onTimeClicked(view: View){


    }


}