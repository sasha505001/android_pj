package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    tableName = "train_done_exercise",
    foreignKeys = [
        ForeignKey(
            entity = TrainsData::class,
            childColumns = ["train_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = ExercisesData::class,
            childColumns = ["exercise_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class TrainsDoneExerciseData (
    @ColumnInfo(name = "train_id")
    var train_id:Int,

    @ColumnInfo(name = "exercise_id")
    var exercise_id:Int,
    ){
    @PrimaryKey(autoGenerate = true)
    var approach:Int = 0
}