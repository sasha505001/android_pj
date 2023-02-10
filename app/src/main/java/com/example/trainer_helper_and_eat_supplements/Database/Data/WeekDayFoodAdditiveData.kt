package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

// Пищевая добавка - день недели
@Entity(
    tableName = "week_days_food_additive",
    primaryKeys = ["week_days_id", "food_additive_id"],
    foreignKeys = [
        ForeignKey(
            entity = FoodAdditiveData::class,
            childColumns = ["food_additive_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = WeekDayData::class,
            childColumns = ["week_days_id"],
            parentColumns = ["id"],
            onDelete = ForeignKey.NO_ACTION
        ),
    ]
)
class WeekDayFoodAdditiveData(
    // День недели
    @ColumnInfo(name = "week_days_id")
    var week_days_id: Int,

    // Пищевая добавка
    @ColumnInfo(name = "food_additive_id")
    var food_additive_id: Int
) {
}