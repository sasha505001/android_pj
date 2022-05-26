package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    tableName = "train_done_exercise",
    primaryKeys = ["train_id", "done_exercise"],
    foreignKeys = [
        ForeignKey(
            entity = TrainsData::class,
            childColumns = ["train_id"],
            parentColumns = ["id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = DoneExercisesData::class,
            childColumns = ["done_exercise"],
            parentColumns = ["id"],
            onDelete = CASCADE
        )
    ]
)
data class TrainsDoneExerciseData (
    @ColumnInfo(name = "train_id")
    var train_id:Int,

    @ColumnInfo(name = "done_exercise")
    var doneExercise_id:Int,
    ){
}