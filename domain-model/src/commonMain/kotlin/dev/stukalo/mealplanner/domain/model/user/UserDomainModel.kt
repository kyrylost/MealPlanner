package dev.stukalo.mealplanner.domain.model.user

import java.util.Date

data class UserDomainModel(
    val id: Long = 0,
    val birthDate: Date,
    val height: Double,
    val weight: Double,
    val physicalActivity: Int,
    val gender: GenderDomainModel,
    val diet: DietDomainModel,
)
