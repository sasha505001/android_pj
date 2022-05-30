package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_measure",
    primaryKeys = ["exercise_id", "measure_id"],
    foreignKeys = [
        ForeignKey(
            entity = ExercisesData::class,
            childColumns = ["exercise_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = MeasuresData::class,
            childColumns = ["measure_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class ExerciseMeasuresData(

    @ColumnInfo(name = "exercise_id")
    var exercise:Int,

    @ColumnInfo(name = "measure_id")
    var measure:Int
) {
}