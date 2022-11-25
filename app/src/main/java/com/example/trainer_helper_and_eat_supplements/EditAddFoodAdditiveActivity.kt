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
import androidx.appcompat.app.AlertDialog
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddComplexActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddFoodAdditiveActivityBinding

class EditAddFoodAdditiveActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    var selectedDaysOfWeek:BooleanArray? = null

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
                            selectedDaysOfWeek = null
                            binding.dayOfWeekText.text = "Дни недели не выбраны"
                        }
                        "В определенный день" -> {
                            binding.dayOfWeekCard.visibility = View.GONE
                            binding.dateOfTakingCard.visibility = View.VISIBLE
                            selectedDaysOfWeek = null
                            binding.dayOfWeekText.text = "Дни недели не выбраны"
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
            val takingOfFoodAdditiveTimes = result.data?.getStringExtra(CONSTANTS.RESULT_MANY_TIME_COUNT_STRING)
            if(takingOfFoodAdditiveTimes != ""){
                binding.timeOfTakingText.setText(takingOfFoodAdditiveTimes)
            }else{
                binding.timeOfTakingText.setText(getString(R.string.default_text_of_taking_time))
            }

        }
    }

    // При нажатии выбора дней недели
    fun onDayOfWeekClicked(view: View){
        Log.d("MyLog", "haha")
        myDatamodel.allDayOfWeekNames.observe(this){ namesDaysOfWeek->

            // Результирующая строка
            var resultStr:String = "Дни недели не выбраны"

            // выбранные дни недели
            var checkedDaysOfWeek = BooleanArray(namesDaysOfWeek.size){false}

            // Если до этого было что то выбрано
            if(selectedDaysOfWeek!= null){
                checkedDaysOfWeek = selectedDaysOfWeek!!.clone()
            }

            // Создаю последовательность char(setMultiChoiceItems требует)
            val array = namesDaysOfWeek.toTypedArray()

            // Создаваемый alertDialog
            val builder = AlertDialog.Builder(this)

            // Заголовок
            builder.setTitle("Выберете дни недели:")

            // Меняю при нажатии выборов bools
            builder.setMultiChoiceItems(array, checkedDaysOfWeek){
                    dialog, which, choice ->
                checkedDaysOfWeek[which] = choice
            }

            // При нажатии кнопики отмены
            builder.setNegativeButton("Отмена"){ dialog, id->
                dialog.dismiss()
            }

            // При нажатии ок
            builder.setPositiveButton("Ок") { dialog, id->
                for((index, item) in checkedDaysOfWeek.withIndex()){
                    if(item){
                        if (resultStr != "Дни недели не выбраны") {
                            resultStr = resultStr + ",\n" + namesDaysOfWeek[index]
                        }
                        else{
                            resultStr = namesDaysOfWeek[index]
                        }
                    }
                }
                selectedDaysOfWeek = checkedDaysOfWeek
                binding.dayOfWeekText.text = resultStr
                dialog.dismiss()
            }

            builder.show()
        }
    }

}