package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.trainer_helper_and_eat_supplements.Database.Data.ComplexesData
import com.example.trainer_helper_and_eat_supplements.LiveData.MyApplication
import com.example.trainer_helper_and_eat_supplements.databinding.EditAddComplexActivityBinding

class EditAddComplexActivity : AppCompatActivity() {

    // Livedata
    private val myDatamodel:MyDataModel by viewModels{
        MyDataModelFactory((MyApplication(this)).myRep)
    }

    // Для выбора элементов
    lateinit var binding:EditAddComplexActivityBinding

    // Имя объекта(только при редактировании)
    var nameOfEditObject:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Заголовок
        supportActionBar?.title = "Комплекс"

        // Заполнение объекта экрана
        binding = EditAddComplexActivityBinding.inflate(layoutInflater)

        // Получение аргументов при редактировании
        var arguments = intent.extras

        if(arguments != null){
            nameOfEditObject = arguments.getString(CONSTANTS.NAMEOFEDITOBJ)
        }

        if(nameOfEditObject!=null){
            binding.complexNameEditText.setText(nameOfEditObject!!)
            var resultList = ""
            myDatamodel.getExercisesNamesByComplexName(nameOfEditObject!!).observe(this){ exerciseNames->
                exerciseNames.forEach(){
                    if(resultList == ""){
                        resultList = it
                    }
                    else{
                        resultList = resultList + ",\n" + it
                    }
                }
                if(resultList != ""){
                    binding.exercisesText.setText(resultList)
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
            // TODO проверка и создание в бд
            saveInDatabase()
        }
        // При нажатии кнопки возврата
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onClickExerciseList(view: View){
        val lastStrOfExercises = binding.exercisesText.text.toString()
        val myIntent = Intent(this, SelectManyExercisesActivity::class.java)
        if(lastStrOfExercises != getString(R.string.default_text_of_choose_exercises)) {
            myIntent.putExtra(CONSTANTS.LAST_EXERCISES_STR, lastStrOfExercises)
        }
        editAddExercise.launch(myIntent)
    }

    // Activity for result
    val editAddExercise = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val exerciseString = result.data?.getStringExtra(CONSTANTS.SELECT_OF_MANY_EXERCISES)
            if(exerciseString != ""){
                binding.exercisesText.setText(exerciseString)
            }else{
                binding.exercisesText.setText(getString(R.string.default_text_of_choose_exercises))
            }

        }
    }

    fun saveInDatabase(){
        // Сообщение об ошибки
        var alertStr:String = ""
        val nameOfCreatingComplex = binding.complexNameEditText.text.toString()
        val chosenExercisesNames = binding.exercisesText.text.toString()
        myDatamodel.allComplexesNames.observe(this){ allComplexes ->



            // Проверка задано ли имя
            if(nameOfCreatingComplex == ""){
                alertStr = alertStr + "Введите имя комплекса;\n"
            }

            // Проверка введено ли имя комплекса

            if(chosenExercisesNames ==
                getString(R.string.default_text_of_choose_exercises)){
                alertStr = alertStr + "Выберете упражнение;\n"
            }

            if (nameOfEditObject!=null){
                if(nameOfCreatingComplex!= nameOfEditObject && allComplexes.contains(nameOfCreatingComplex)){
                    alertStr = "Комплекс с введенным именем уже существует"
                }
            }else{
                if(allComplexes.contains(nameOfCreatingComplex)){
                    alertStr = "Комплекс с введенным именем уже существует"
                }
            }

        }
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
            val data = ComplexesData(nameOfCreatingComplex)
            val exercisesNames = chosenExercisesNames.split(",\n")
            if(nameOfEditObject==null){
                myDatamodel.insertFullComplex(data, exercisesNames)
            }
            else{
                myDatamodel.updateComplex(nameOfEditObject!!, data, exercisesNames)
            }

            finish()
        }
    }
}
