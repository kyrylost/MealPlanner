package dev.stukalo.mealplanner.data.network.edamam.recipe.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EdamamHitNetModel(
    @SerialName("recipe") val recipe: EdamamRecipeNetModel? = null,
)
