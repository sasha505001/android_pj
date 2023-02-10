package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Комплексы упражнений
@Entity(tableName = "complex",
    indices = [androidx.room.Index(
        value = ["name"],
        unique = true,
    )])
data class ComplexData(
    // Название комплекса
    @ColumnInfo(name = "name")
    val name:String
) {
    // Id комплекса
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}