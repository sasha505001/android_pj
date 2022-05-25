package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "done_exercise",
    foreignKeys = [
        ForeignKey(
            entity = ExercisesData::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = MeasuresData::class,
            parentColumns = ["id"],
            childColumns = ["measure_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
class DoneExercisesData(

    @ColumnInfo(name = "exercise_id")
    var exercise:Int?,

    @ColumnInfo(name = "measure_id")
    var mesure:Int?,

    @ColumnInfo(name = "count")
    var count:Float,
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}