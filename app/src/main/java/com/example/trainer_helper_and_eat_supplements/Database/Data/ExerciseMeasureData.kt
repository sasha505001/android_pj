package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

// упражнение - меры
@Entity(
    tableName = "exercise_measure",
    primaryKeys = ["exercise_id", "measure_id"],
    foreignKeys = [
        ForeignKey(
            entity = ExerciseData::class,
            childColumns = ["exercise_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = MeasureData::class,
            childColumns = ["measure_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class ExerciseMeasureData(

    // ID упражнения
    @ColumnInfo(name = "exercise_id")
    var exercise_id:Int,

    // ID меры
    @ColumnInfo(name = "measure_id")
    var measure_id:Int
) {
}