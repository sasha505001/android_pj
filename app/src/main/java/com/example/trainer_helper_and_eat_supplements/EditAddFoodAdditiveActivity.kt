package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
            Log.d("MyLog", "Ok")
            // TODO проверка и создание в бд
            //saveInDatabase()
        }
        // При нажатии кнопки возврата
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // При нажатии на время
    fun onTimeClicked(view: View){
        val intent = Intent(this, TimeOfTakingFoodAdditives::class.java)
        setTimeOftakingFoodAdditive.launch(intent)

    }
    val setTimeOftakingFoodAdditive = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val exerciseString = result.data?.getStringExtra(CONSTANTS.SELECT_OF_MANY_EXERCISES)
            if(exerciseString != ""){
                // binding.exercisesText.setText(exerciseString)
            }else{
                binding.timeOfTakingText.setText(getString(R.string.default_text_of_taking_time))
            }

        }
    }

}