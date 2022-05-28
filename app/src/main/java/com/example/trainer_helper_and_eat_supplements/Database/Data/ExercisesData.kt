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
    var name:String,

    // Картинка
    @ColumnInfo(name = "image")
    var myImage: String = "",

    // Ссылка
    @ColumnInfo(name = "link")
    var link: String = "",

    ){
    // ID
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}

