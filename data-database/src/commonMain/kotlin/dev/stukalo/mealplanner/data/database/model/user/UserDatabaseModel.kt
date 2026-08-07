package dev.stukalo.mealplanner.data.database.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity
data class UserDatabaseModel(
    @PrimaryKey
    val id: Long = DEFAULT_USER_ID,
    val name: String,
    val birthDate: LocalDate,
    val height: Double,
    val physicalActivity: ActivityLevelDatabaseModel,
    val gender: GenderDatabaseModel,
    val diet: DietDatabaseModel
) {
    companion object {
        const val DEFAULT_USER_ID = 1L
    }
}
