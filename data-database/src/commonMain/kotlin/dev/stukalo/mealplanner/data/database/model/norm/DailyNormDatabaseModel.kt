package dev.stukalo.mealplanner.data.database.model.norm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyNormDatabaseModel(
    @PrimaryKey
    val id: Long = 0,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double
)
