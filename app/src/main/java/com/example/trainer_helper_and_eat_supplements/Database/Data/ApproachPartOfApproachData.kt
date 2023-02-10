package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "approach_part_of_approach",
    foreignKeys = [
        ForeignKey(
            entity = ApproachData::class,
            parentColumns = ["id"],
            childColumns = ["approach_id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = PartOfApproachData::class,
            parentColumns = ["id"],
            childColumns = ["part_of_approach_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
class ApproachPartOfApproachData (
    // подход
    @ColumnInfo(name = "approach_id")
    val approach_id:Int,

    // часть данных о подходе
    @ColumnInfo(name = "part_of_approach_id")
    val part_of_approach_id:Int
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}