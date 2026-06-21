package dev.stukalo.mealplanner.data.database.model.progress

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity
data class DailyProgressDatabaseModel(
    @PrimaryKey
    val date: LocalDate,
    val consumedCalories: Double,
    val consumedProteins: Double,
    val consumedFats: Double,
    val consumedCarbohydrates: Double
)
