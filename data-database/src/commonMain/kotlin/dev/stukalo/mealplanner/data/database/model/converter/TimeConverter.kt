package dev.stukalo.mealplanner.data.database.model.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalTime

class TimeConverter {
    @TypeConverter
    fun fromString(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it) }
    }

    @TypeConverter
    fun timeToString(time: LocalTime?): String? {
        return time?.toString()
    }
}