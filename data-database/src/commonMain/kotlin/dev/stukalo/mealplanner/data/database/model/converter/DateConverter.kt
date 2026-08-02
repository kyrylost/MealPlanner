package dev.stukalo.mealplanner.data.database.model.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class DateConverter {
    @TypeConverter
    fun fromEpochDays(value: Long?): LocalDate? = value?.let { LocalDate.fromEpochDays(it) }

    @TypeConverter
    fun dateToEpochDays(date: LocalDate?): Long? = date?.toEpochDays()
}
