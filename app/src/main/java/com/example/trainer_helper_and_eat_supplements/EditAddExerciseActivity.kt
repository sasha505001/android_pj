package com.example.trainer_helper_and_eat_supplements

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            // TODO проверка и создание в бд
            saveInDatabase()
        }
        // При нажатии кнопки возврата
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // Проверка данных и их сохранение в бд
    private fun saveInDatabase(){
        // Сообщение об ошибки
        var alertStr:String = ""

        // Проверка задано ли имя
        if(binding.nameOfExercise.text.toString() == ""){
            alertStr = alertStr + "Введите имя упражнения;\n"
        }
        else{
            // Проверка существует ли упражнение с данным именем
            myDatamodel.allExercisesName.observe(this){
                val list = it
                if(list.contains(binding.nameOfExercise.text.toString())){
                    alertStr ="Упражнение с данным именем уже существует\n"
                }
            }
        }
        // Проверка введена ли мера
        if(binding.mesureText.text.toString() == getString(R.string.default_value_of_measures)){
            alertStr = alertStr + "Введите меры;\n"
        }

        // Проверка заполнены ли все необходимые поля
        if(alertStr != ""){
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Невозможно создать упражнение")
            alertDialog.setMessage(alertStr)
            alertDialog.setPositiveButton("Ок"){
                    dialog, id ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
        else{
            val data = ExercisesData(
                binding.nameOfExercise.text.toString(),
                "",// TODO сделать для картинки
                binding.linkEditText.text.toString()
            )
            myDatamodel.insertExercise(data)
            val text: String = binding.mesureText.text.toString()

            var selectedMesures = text.split(",\n")
            for (i in selectedMesures){
                var id:Int = 0
                myDatamodel.getMesureByName(i).observe(this){
                    id = it.id
                }


            }
            myDatamodel.allMesures.observe(this){
                val list = it
            }

            finish()
        }

    }

    // При нажатии на выбор меры измерения
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
                selectedItemsOfMesure = checkedItemsArray
                binding.mesureText.text = resultStr
                dialog.dismiss()
            }

            builder.show()
        }


    }


}