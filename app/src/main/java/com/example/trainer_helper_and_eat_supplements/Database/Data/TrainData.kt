package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

// Тренировка
@Entity(tableName = "train",
    foreignKeys = [
        ForeignKey(
            entity = ComplexData::class,
            childColumns = ["complex_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class TrainData(
    // Название
    @ColumnInfo(name = "name")
    var name:String,

    // Комплекс
    @ColumnInfo(name = "complex_id")
    var complex_id:Int,

    // Дата начала тренировки
    @ColumnInfo(name = "datetime_start_of_train")
    var datetime_start_of_train: Date,
    ) {
    // Id
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}