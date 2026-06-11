package dev.stukalo.mealplanner.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.stukalo.mealplanner.data.database.model.converter.DateConverter
import java.util.Date

@Entity
@TypeConverters(DateConverter::class)
data class UserDatabaseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val birthDate: Date,
    val height: Double,
    val weight: Double,
    val physicalActivity: Int,
    val gender: GenderDatabaseModel,
    val diet: DietDatabaseModel,
)
