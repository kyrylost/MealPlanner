package dev.stukalo.mealplanner.data.network.edamam.recipe.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EdamamIngredientNetModel(
    @SerialName("text") val text: String? = null,
    @SerialName("quantity") val quantity: Double? = null,
    @SerialName("measure") val measure: String? = null,
    @SerialName("food") val food: String? = null,
    @SerialName("weight") val weight: Double? = null,
    @SerialName("foodCategory") val foodCategory: String? = null,
    @SerialName("foodId") val foodId: String,
    @SerialName("image") val image: String? = null
)
