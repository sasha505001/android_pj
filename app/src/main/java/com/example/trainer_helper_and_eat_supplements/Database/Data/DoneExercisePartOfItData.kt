package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "done_exercise_and_part",
    foreignKeys = [
        ForeignKey(
            entity = TrainsDoneExerciseData::class,
            parentColumns = ["approach"],
            childColumns = ["approach_id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = PartOfDoneExercisesData::class,
            parentColumns = ["id"],
            childColumns = ["part_of_done_exercise"],
            onDelete = ForeignKey.CASCADE
        )
    ])
class DoneExercisePartOfItData (
    @ColumnInfo(name = "approach_id")
    val approach_id:Int,
    @ColumnInfo(name = "part_of_done_exercise")
    val part_of_done_exercise:Int
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}