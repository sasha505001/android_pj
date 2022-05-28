package com.example.trainer_helper_and_eat_supplements

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.Data.MeasuresData
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddExerciseActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.MainListActivityBinding

class EditAddExerciseActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // Для выбора элементов
    lateinit var binding: EditAddExerciseActivityBinding

    var selectedItemsOfMesure:BooleanArray? = null

    // Имя объекта(только при редактировании)
    var nameOfEditObject:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заполнение объекта экрана
        binding = EditAddExerciseActivityBinding.inflate(layoutInflater)

        // Получение аргументов при редактировании
        var arguments = intent.extras
        if(arguments != null){
            nameOfEditObject = arguments.getString(CONSTANTS.NAMEOFEDITOBJ)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ok_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ok_btn){
            // TODO проверка и создание в бд
            myDatamodel.insertExercise(ExercisesData("asd","asd", "asd"))
            //saveInDatabase()
        }
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveInDatabase(){
        var alertStr:String = ""
        var data:ExercisesData?

        // Обязательно должно быть выбрано имя и мера
        if(binding.nameOfExercise.text.toString() == ""){
            alertStr = alertStr + "Введите имя упражнения;\n"
        }
        else{
            myDatamodel.allExercisesName.observe(this){
                var listExer = it
                if(listExer.contains(binding.nameOfExercise.text.toString())){
                    alertStr = alertStr + "Упражнение с данным именем уже существует\n"
                }
            }
        }
        if(binding.mesureText.text.toString() == getString(R.string.default_value_of_measures)){
            alertStr = alertStr + "Введите меры;\n"
        }


        if(alertStr == ""){
            data = ExercisesData(
                binding.nameOfExercise.text.toString(),
                "",
                ""
            )

            finish()
            var exer:ExercisesData = ExercisesData("haha")

        }
    }

    fun onMesureClicked(view:View){
        // получение выбранных мер
        myDatamodel.allMesuresName.observe(this){ choices->
            // Результирующая строка
            var resultStr:String = getString(R.string.default_value_of_measures)

            // текущие меры
            var checkedItemsArray = BooleanArray(choices.size){false}

            // Если до этого создавался
            if(selectedItemsOfMesure != null){
                checkedItemsArray = selectedItemsOfMesure!!.clone()
            }

            // Создаю последовательность char(setMultiChoiceItems требует)
            val array = choices.toTypedArray()

            // Создаваемый alertDialog
            val builder = AlertDialog.Builder(this)

            // Заголовок
            builder.setTitle("Выберете меру:")
            // Меняю при нажатии выборов bools
            builder.setMultiChoiceItems(array, checkedItemsArray){
                    dialog, which, choice ->
                checkedItemsArray[which] = choice
            }
            builder.setNegativeButton("Отмена"){ dialog, id->
                dialog.dismiss()
            }
            // При нажатии ок
            builder.setPositiveButton("Ок") { dialog, id->
                for((index, item) in checkedItemsArray.withIndex()){
                   if(item){
                       if (resultStr != getString(R.string.default_value_of_measures)) {
                           resultStr = resultStr + ",\n" + choices[index]
                       }
                       else{
                           resultStr = choices[index]
                       }
                   }
                }
                Toast.makeText(this,"haha", Toast.LENGTH_SHORT).show()
                selectedItemsOfMesure = checkedItemsArray
                binding.mesureText.text = resultStr
                dialog.dismiss()
            }

            builder.show()
        }


    }


}