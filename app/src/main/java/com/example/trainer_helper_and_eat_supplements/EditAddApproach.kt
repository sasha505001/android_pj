package com.example.trainer_helper_and_eat_supplements

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.trainer_helper_and_eat_supplements.databinding.ActivityEditAddApproachBinding
import com.example.trainer_helper_and_eat_supplements.databinding.ActivityTrainBinding

class EditAddApproach : AppCompatActivity() {
    
    lateinit var binding: ActivityEditAddApproachBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAddApproachBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Подход упражнения"
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ok_menu, menu)
        return true
    }
}