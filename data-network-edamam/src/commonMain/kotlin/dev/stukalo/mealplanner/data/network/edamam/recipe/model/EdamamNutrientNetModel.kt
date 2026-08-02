package dev.stukalo.mealplanner.data.network.edamam.recipe.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EdamamNutrientNetModel(
    @SerialName("label") val label: String? = null,
    @SerialName("quantity") val quantity: Double? = null,
    @SerialName("unit") val unit: String? = null
)
