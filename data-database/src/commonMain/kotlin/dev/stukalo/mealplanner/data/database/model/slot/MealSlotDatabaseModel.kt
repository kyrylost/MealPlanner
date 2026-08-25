package dev.stukalo.mealplanner.data.database.model.slot

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

/**
 * Database entity representing a meal slot.
 *
 * @property id Unique identifier (auto-generated).
 * @property startTime The scheduled time for the meal.
 * @property proteinsPercentage Target percentage of daily protein intake for this slot.
 * @property fatsPercentage Target percentage of daily fat intake for this slot.
 * @property carbsPercentage Target percentage of daily carbohydrate intake for this slot.
 * @property mealType The type of meal.
 * @property lastConsumedDate The date when the meal was last consumed.
 */
@Entity
data class MealSlotDatabaseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val startTime: LocalTime,
    val proteinsPercentage: Int,
    val fatsPercentage: Int,
    val carbsPercentage: Int,
    val mealType: MealTypeDatabaseModel,
    val lastConsumedDate: LocalDate? = null
)
