package com.example.trainer_helper_and_eat_supplements.Database.Data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Упражнение
@Entity(tableName = "exercise",
    indices = [Index(
        value = ["name"],
        unique = true
    )])
data class ExerciseData (

    // Название упражнения
    @ColumnInfo(name = "name")
    val name:String,

    // Картинка
    @ColumnInfo(name = "image")
    val myImage: String = "",

    // Ссылка
    @ColumnInfo(name = "link")
    val link: String = "",

    // Время отдыха(возможно ненужно)
    @ColumnInfo(name = "rest_time")
    val rest_time:Int = 90
    ){
    // ID
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}

