package dev.stukalo.mealplanner.data.network.edamam.recipe.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EdamamLinksNetModel(@SerialName("next") val next: EdamamNextNetModel? = null)
