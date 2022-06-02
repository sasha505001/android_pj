package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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

    var lastExercises:String? = null

    // Для выбора элементов
    lateinit var binding:SelectManyExercisesActivityBinding

    // При создании activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заполнение объекта экрана
        binding = SelectManyExercisesActivityBinding.inflate(layoutInflater)

        // Получение аргументов
        var arguments = intent.extras
        // Получение имени комлпекса
        if(arguments != null){
            lastExercises = arguments.getString(CONSTANTS.LAST_EXERCISES_STR)
        }

        myDatamodel.allExercisesName.observe(this){ exercises ->
            val adapter = SelectManyExerciseAdapter(exercises, myDatamodel, this)
            binding.exercisesSelect.layoutManager = LinearLayoutManager(this)
            binding.exercisesSelect.adapter = adapter
            myDatamodel.allBoolExercise.value = BooleanArray(exercises.size){false}
            if(lastExercises!=null){
                var exerNames:List<String> = lastExercises!!.split(",\n")
                exerNames.forEach(){ curExercise ->
                    val curI = exercises.indexOf(curExercise)
                    myDatamodel.allBoolExercise.value!![curI] = true
                }
            }
        }

        // Кнопка возвращения на прошлое меню
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Заголовок
        supportActionBar?.title = "Выберете упражнения"

        //
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

            myDatamodel.allExercisesName.observe(this){ exerciseNames ->
                myDatamodel.allBoolExercise.observe(this){ allBools->
                    var exerciseStr:String = ""
                    allBools.forEachIndexed(){ i, curBool->
                        if(curBool){
                            if(exerciseStr==""){
                                exerciseStr = exerciseNames[i]
                            }
                            else{
                                exerciseStr = exerciseStr + ",\n" + exerciseNames[i]
                            }
                        }
                    }
                    var myIntent = Intent()
                    myIntent.putExtra(CONSTANTS.SELECT_OF_MANY_EXERCISES, exerciseStr)
                    setResult(RESULT_OK, myIntent)
                }
            }
            finish()
            Toast.makeText(this, "Сохранение", Toast.LENGTH_SHORT).show()
        }
        // При нажатии кнопки возврата
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}