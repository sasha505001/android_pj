package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

// Пищевая добавка - расписание принятия
@Entity(
    tableName = "taking_time_food_additive",
    primaryKeys = ["taking_time_id", "food_additive_id"],
    foreignKeys = [
        ForeignKey(
            entity = FoodAdditiveData::class,
            childColumns = ["food_additive_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = TakingTimeAndDoseData::class,
            childColumns = ["taking_time_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        ),
    ]
)
class FoodAdditiveTakingTimeAndDoseData(
    @ColumnInfo(name = "taking_time_id")
    var taking_time_id: Int,
    @ColumnInfo(name = "food_additive_id")
    var food_additive_id: Int
) {

}