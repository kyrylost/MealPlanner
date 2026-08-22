package dev.stukalo.mealplanner.data.database.model.converter

import androidx.room.TypeConverter
import dev.stukalo.mealplanner.data.database.model.slot.MealTypeDatabaseModel

/**
 * Room TypeConverter for [MealTypeDatabaseModel].
 * Converts between the enum and its string representation.
 */
class MealTypeConverter {
    @TypeConverter
    fun fromString(value: String?): MealTypeDatabaseModel? = value?.let { MealTypeDatabaseModel.valueOf(it) }

    @TypeConverter
    fun toString(value: MealTypeDatabaseModel?): String? = value?.name
}
