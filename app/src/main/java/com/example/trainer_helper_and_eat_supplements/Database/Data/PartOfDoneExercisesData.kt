package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "part_of_done_exercises",
    foreignKeys = [
        ForeignKey(
            entity = MeasuresData::class,
            parentColumns = ["id"],
            childColumns = ["measure_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class PartOfDoneExercisesData(

    @ColumnInfo(name = "measure_id")
    var mesure:Int,

    @ColumnInfo(name = "count")
    var count:Float,
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}