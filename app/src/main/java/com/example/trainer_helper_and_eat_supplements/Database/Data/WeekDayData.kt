package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// День недели
@Entity(tableName = "week_days")
class WeekDayData (
    // Название дня недели
    @ColumnInfo(name = "name")
    val name:String,
){
    // ID
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}