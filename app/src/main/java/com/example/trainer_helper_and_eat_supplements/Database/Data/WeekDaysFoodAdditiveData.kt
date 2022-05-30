package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "week_days_food_additive_data",
    primaryKeys = ["week_days_id", "food_additive_id"],
    foreignKeys = [
        ForeignKey(
            entity = FoodAdditiveData::class,
            childColumns = ["food_additive_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = WeekDaysData::class,
            childColumns = ["week_days_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        ),
    ]
)
class WeekDaysFoodAdditiveData(
    @ColumnInfo(name = "week_days_id")
    var week_days_id: Int,
    @ColumnInfo(name = "food_additive_id")
    var food_additive_id: Int
) {
}