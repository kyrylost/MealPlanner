package dev.stukalo.mealplanner.domain.model.user

import kotlinx.datetime.LocalDate

data class UserDomainModel(
    val id: Long = 0,
    val name: String,
    val birthDate: LocalDate,
    val height: Double,
    val weight: Double,
    val physicalActivity: ActivityLevelDomainModel,
    val gender: GenderDomainModel,
    val diet: DietDomainModel,
)
