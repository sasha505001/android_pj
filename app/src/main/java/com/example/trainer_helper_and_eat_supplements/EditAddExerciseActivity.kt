package com.example.trainer_helper_and_eat_supplements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.trainer_helper_and_eat_supplements.Database.MyDatabase
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddExerciseActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.MainListActivityBinding

class EditAddExerciseActivity : AppCompatActivity() {

    lateinit var binding: EditAddExerciseActivityBinding

    // Имя объекта(только при редактировании)
    var nameOfEditObject:String? = null

    // База данных
    var database: MyDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заполнение объекта экрана
        binding = EditAddExerciseActivityBinding.inflate(layoutInflater)

        var arguments = intent.extras

        if(arguments != null){
            nameOfEditObject = arguments.getString("")
        }

        // Запуск базы данных
        Thread{
            database =  MyDatabase.getDatabase(this)
        }.start()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.edit_add_exercise_activity)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ok_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ok_btn){
            // TODO проверка и создание в бд
            if(binding.nameOfExercise.text.toString() == ""){
                Toast.makeText(this, "haha", Toast.LENGTH_SHORT).show()
            }
        }
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveInDatabase(){
        
    }


}