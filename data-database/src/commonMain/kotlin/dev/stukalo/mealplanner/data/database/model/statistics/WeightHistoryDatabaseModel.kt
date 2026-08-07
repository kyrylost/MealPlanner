package dev.stukalo.mealplanner.data.database.model.statistics

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity
data class WeightHistoryDatabaseModel(
    @PrimaryKey
    val date: LocalDate,
    val weight: Double
)
