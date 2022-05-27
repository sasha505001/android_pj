package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "measure",
    indices = [Index(
        value = ["name"],
        unique = true
    )])
data class MeasuresData(
    // Название упражнения
    @ColumnInfo(name = "name")
    val measure_name:String
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}