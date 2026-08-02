package dev.stukalo.mealplanner.data.database.model.converter

import androidx.room.TypeConverter
import dev.stukalo.mealplanner.data.database.model.slot.MealTypeDatabaseModel

class MealTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<MealTypeDatabaseModel>? =
        value?.split(",")?.filter { it.isNotEmpty() }?.map { MealTypeDatabaseModel.valueOf(it) }

    @TypeConverter
    fun listToString(list: List<MealTypeDatabaseModel>?): String? = list?.joinToString(",") { it.name }
}
