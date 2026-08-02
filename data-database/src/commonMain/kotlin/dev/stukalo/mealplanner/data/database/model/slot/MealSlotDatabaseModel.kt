package dev.stukalo.mealplanner.data.database.model.slot

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalTime

@Entity
data class MealSlotDatabaseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val startTime: LocalTime,
    val proteinsPercentage: Int,
    val fatsPercentage: Int,
    val carbsPercentage: Int,
    val mealTypes: List<MealTypeDatabaseModel>,
    val isConsumed: Boolean = false
)
