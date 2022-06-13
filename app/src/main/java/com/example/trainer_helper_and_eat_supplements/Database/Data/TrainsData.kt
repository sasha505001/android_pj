package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "train",
    foreignKeys = [
        ForeignKey(
            entity = ComplexesData::class,
            childColumns = ["complex_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class TrainsData(
    @ColumnInfo(name = "name")
    var name:String,

    @ColumnInfo(name = "complex_id")
    var complex:Int,

    @ColumnInfo(name = "date_of_train")
    var daytime: Date,
    ) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}