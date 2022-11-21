package com.example.trainer_helper_and_eat_supplements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.trainer_helper_and_eat_supplements.databinding.ActivityTrainBinding
import com.example.trainer_helper_and_eat_supplements.databinding.EditTrainActivityBinding

class TrainActivity : AppCompatActivity() {

    lateinit var binding: ActivityTrainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Тренировка"
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.train_menu, menu)
        return true
    }

    fun onAddApproachClicked(view: View){
        val myIntent = Intent(this, EditAddApproach::class.java)
        startActivity(myIntent)
    }
}