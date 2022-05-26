package com.example.trainer_helper_and_eat_supplements.Database.Data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise",
    indices = [androidx.room.Index(
        value = ["name"],
        unique = true
    )])
data class ExercisesData (

    // Название упражнения
    @ColumnInfo(name = "name")
    val name:String,

    // Картинка
    @ColumnInfo(name = "image")
    val myImage: String? = null,

    // Ссылка
    @ColumnInfo(name = "link")
    val link: String? = null,

    ){
    // ID
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}

