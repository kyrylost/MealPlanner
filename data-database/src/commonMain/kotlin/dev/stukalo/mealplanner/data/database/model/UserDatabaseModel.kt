package dev.stukalo.mealplanner.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.stukalo.mealplanner.data.database.model.converter.DateConverter
import kotlinx.datetime.LocalDate

@Entity
@TypeConverters(DateConverter::class)
data class UserDatabaseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val birthDate: LocalDate,
    val height: Double,
    val weight: Double,
    val physicalActivity: ActivityLevelDatabaseModel,
    val gender: GenderDatabaseModel,
    val diet: DietDatabaseModel,
)
