package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

// Принятие таблеток
@Entity(tableName = "taking_time_and_count")
class TakingTimeAndDoseData(
    // Принимаемая доза
    @ColumnInfo(name = "dose_taken")
    val dose_taken:Float,

    // Время принятия таблеток
    @ColumnInfo(name = "taking_time")
    val taking_time: Date

) {
    // ID
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

}