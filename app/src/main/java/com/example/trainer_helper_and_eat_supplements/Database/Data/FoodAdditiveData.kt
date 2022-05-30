package com.example.trainer_helper_and_eat_supplements.Database.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*


// Уникальна по имени
@Entity(tableName = "food_additive",
        indices = [androidx.room.Index(
                value = ["name"],
                unique = true
        )],
        foreignKeys = [
                ForeignKey(
                        entity = MeasureOfFoodAdditiveData::class,
                        childColumns = ["mesure_id"],
                        parentColumns = ["id"],
                        onDelete = ForeignKey.NO_ACTION
                ),
                ForeignKey(
                        entity = ScheduleData::class,
                        childColumns = ["schedule_id"],
                        parentColumns = ["id"],
                        onDelete = ForeignKey.NO_ACTION
                )
        ])
class FoodAdditiveData (

        // Название Пищевой добавки
        @ColumnInfo(name = "name")
        val name: String,

        // Мера пищевой добавки(ссылка)
        @ColumnInfo(name = "mesure_id")
        val mesure_id: Int,

        // Расписание(тип расписания(каждый день..)
        @ColumnInfo(name = "schedule_id")
        val schedule_id:Int,

        // Интервалы в днях - используется только для интевалы в днях(Расписание)
        @ColumnInfo(name = "intervalOfTakingInDays")
        val intervalOfTakingInDays:Int?,


        // По определенным дням
        // spectable

        // каждый день все равно

        // В определенный день
        @ColumnInfo(name = "dayOfTaking")
        val day: Date?,
)
{
        // ID
        @PrimaryKey(autoGenerate = true)
        var id:Int = 0
}