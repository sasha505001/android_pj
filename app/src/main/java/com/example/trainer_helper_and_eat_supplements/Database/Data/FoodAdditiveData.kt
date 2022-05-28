package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_additive_data",
        indices = [androidx.room.Index(
                value = ["name"],
                unique = true
        )])
class FoodAdditiveData (
        // Название Пищевой добавки
        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "schedule_ID")
        val scheduleID:Int,

        @ColumnInfo(name = "intervalOfTakingInDays")
        val intervalOfTakingInDays:Int

)
{
        // ID
        @PrimaryKey(autoGenerate = true)
        var id:Int = 0
}