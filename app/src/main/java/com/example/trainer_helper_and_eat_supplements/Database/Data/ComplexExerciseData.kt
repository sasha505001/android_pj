package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

// Связывает комплексы и упражнения
@Entity(
    tableName = "complex_exercise",
    primaryKeys = ["exercise_id", "complex_id"],
    foreignKeys = [
        ForeignKey(
            entity = ComplexData::class,
            parentColumns = ["id"],
            childColumns = ["complex_id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = ExerciseData::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class ComplexExerciseData(
    // Id упражнения
    @ColumnInfo(name = "exercise_id")
    var exercise_id:Int,

    // Id комплекса
    @ColumnInfo(name = "complex_id")
    var complex_id:Int,
) {
}