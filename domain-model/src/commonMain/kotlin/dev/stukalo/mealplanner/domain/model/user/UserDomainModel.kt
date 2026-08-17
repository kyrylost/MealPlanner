package dev.stukalo.mealplanner.domain.model.user

import kotlinx.datetime.LocalDate

/**
 * Domain model for a user profile.
 * Supports a single user with a fixed ID.
 */
data class UserDomainModel(
    val id: Long = DEFAULT_USER_ID,
    val name: String,
    val birthDate: LocalDate,
    val height: Double,
    val weight: Double,
    val targetWeight: Double,
    val physicalActivity: ActivityLevelDomainModel,
    val gender: GenderDomainModel,
    val diet: DietDomainModel,
    val stepsTarget: Int
) {
    companion object {
        private const val DEFAULT_USER_ID = 1L
    }
}
