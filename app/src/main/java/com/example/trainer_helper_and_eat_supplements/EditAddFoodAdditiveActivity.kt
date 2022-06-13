package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
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

            binding.scheduleSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when(scheduleNames[p2]){
                        "Каждый день" -> {
                            binding.dayOfWeekCard.visibility = View.GONE
                            binding.dateOfTakingCard.visibility = View.GONE
                        }
                        "В определенный день" -> {
                            binding.dayOfWeekCard.visibility = View.GONE
                            binding.dateOfTakingCard.visibility = View.VISIBLE
                        }
                        "По определенным дням недели" -> {
                            binding.dayOfWeekCard.visibility = View.VISIBLE
                            binding.dateOfTakingCard.visibility = View.GONE
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
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
        val textOfTime:String = binding.timeOfTakingText.text.toString()
        val myIntent = Intent(this, TimeOfTakingFoodAdditives::class.java)
        if (textOfTime != getString(R.string.default_text_of_taking_time)){
            myIntent.putExtra(CONSTANTS.OLD_TIME_AND_COUNT, textOfTime)
        }
        setTimeOftakingFoodAdditive.launch(myIntent)

    }

    val setTimeOftakingFoodAdditive = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val exerciseString = result.data?.getStringExtra(CONSTANTS.RESULT_MANY_TIME_COUNT_STRING)
            if(exerciseString != ""){
                binding.timeOfTakingText.setText(exerciseString)
            }else{
                binding.timeOfTakingText.setText(getString(R.string.default_text_of_taking_time))
            }

        }
    }

}