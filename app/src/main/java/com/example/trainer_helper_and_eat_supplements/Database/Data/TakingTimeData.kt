package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "TakingTimeData")
class TakingTimeData(

    @ColumnInfo(name = "dose_taken")
    val dose_taken:Float,

    @ColumnInfo(name = "taking_time")
    val taking_time: Date

) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

}