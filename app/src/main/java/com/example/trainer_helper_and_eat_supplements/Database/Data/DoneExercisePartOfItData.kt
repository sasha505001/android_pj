package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "DoneExerciseAndPartData",
    foreignKeys = [
        ForeignKey(
            entity = TrainsDoneExerciseData::class,
            parentColumns = ["approach"],
            childColumns = ["done_exercise_id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = PartOfDoneExercisesData::class,
            parentColumns = ["id"],
            childColumns = ["part_of_done_exercise"],
            onDelete = ForeignKey.NO_ACTION
        )
    ])
class DoneExercisePartOfItData (
    @ColumnInfo(name = "done_exercise_id")
    val done_exercise_id:Int,
    @ColumnInfo(name = "part_of_done_exercise")
    val part_of_done_exercise:Int
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}