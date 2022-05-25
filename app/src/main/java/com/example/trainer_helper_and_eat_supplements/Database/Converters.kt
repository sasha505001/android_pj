package com.example.trainer_helper_and_eat_supplements.Database

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
        fun fromTimestamp(value: Long): Date{
        return value.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long{
        return date.time.toLong()
    }
}