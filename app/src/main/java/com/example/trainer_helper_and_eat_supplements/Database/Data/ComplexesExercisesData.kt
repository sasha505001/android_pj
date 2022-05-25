package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    tableName = "complex_exercise",
    primaryKeys = ["exercise_id", "complex_id"],
    foreignKeys = [
        ForeignKey(
            entity = ComplexesData::class,
            parentColumns = ["id"],
            childColumns = ["complex_id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = ExercisesData::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = CASCADE
        )
    ]
)
class ComplexesExercisesData(
    @ColumnInfo(name = "exercise_id")
    var exercise:Int,

    @ColumnInfo(name = "complex_id")
    var complex:Int,
) {
}