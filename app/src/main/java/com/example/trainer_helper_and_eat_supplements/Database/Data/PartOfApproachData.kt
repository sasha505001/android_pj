package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "part_of_approach",
    foreignKeys = [
        ForeignKey(
            entity = MeasureData::class,
            parentColumns = ["id"],
            childColumns = ["measure_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class PartOfApproachData(
    // Мера
    @ColumnInfo(name = "measure_id")
    var mesure:Int,

    // Количество
    @ColumnInfo(name = "count")
    var count:Float,
) {
    // Id
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}