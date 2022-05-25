package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "complex",
    indices = [androidx.room.Index(
        value = ["name"],
        unique = true,
    )])
class ComplexesData(
    @ColumnInfo(name = "name")
    var name:String
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}