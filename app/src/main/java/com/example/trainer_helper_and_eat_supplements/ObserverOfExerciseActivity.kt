package com.example.trainer_helper_and_eat_supplements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddExerciseActivityBinding
import com.example.trainer_helper_and_eat_supplements.databinding.ObserverOfExerciseActivityBinding

// Окно отображения упражнения
class ObserverOfExerciseActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // Для выбора элементов
    lateinit var binding: ObserverOfExerciseActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Наполняю для редактирования
        binding = ObserverOfExerciseActivityBinding.inflate(layoutInflater)

        // Получение аргументов для отображения
        var arguments = intent.extras
        if (arguments!=null){
            val nameOfExercise = arguments.getString(CONSTANTS.NAMEOFOBSERVE)
            supportActionBar?.title = "Упражнение"
                myDatamodel.getExerciseByName(nameOfExercise!!).observe(this){ exercise ->
                // Задаю имя
                binding.nameText.setText(exercise.name)
                // Задаю все меры
                myDatamodel.getMesuresFromExerciseId(exercise.id).observe(this){ mesures->
                    var mesuresString:String = ""
                    mesures.forEach(){
                        if (mesuresString == ""){
                            mesuresString = it
                        }
                        else{
                            mesuresString = mesuresString + ",\n" + it
                        }
                    }
                    binding.mesureText.setText(mesuresString)
                }
                // Для изображения
                // TODO сделать с изображением
                binding.pictureText.setText("Изображение не выбрано")

                binding.linkText.setText(exercise.link)
            }
        }

        // Кнопка возврата
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // При нажатии кнопки возврата
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}