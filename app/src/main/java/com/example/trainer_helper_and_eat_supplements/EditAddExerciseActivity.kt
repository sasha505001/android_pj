package com.example.trainer_helper_and_eat_supplements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.example.trainer_helper_and_eat_supplements.Database.Data.ExercisesData
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddExerciseActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.MainListActivityBinding

class EditAddExerciseActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    lateinit var binding: EditAddExerciseActivityBinding

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
            saveInDatabase()
        }
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveInDatabase(){
        var alertStr:String = ""
        var data:ExercisesData?
        // Проверка на заполнение данных
        if(binding.nameOfExercise.text.toString() == ""){
            alertStr = alertStr + "Введите имя упражнения\n"
            Toast.makeText(this, binding.nameOfExercise.text.toString(), Toast.LENGTH_SHORT).show()
        }
        /* TODO проверка на одинаковые имена
        if(binding.mesureText.text.toString() == getString(R.string.default_value_of_measures)){
            alertStr = alertStr + "Введите меры\n"
        }*/
        if(alertStr == ""){
            data = ExercisesData(binding.nameOfExercise.text.toString())

            finish()
        }
    }


}