package dev.stukalo.mealplanner.data.database.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity
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
