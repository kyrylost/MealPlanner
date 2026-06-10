package dev.stukalo.mealplanner.data.network.edamam.recipe.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EdamamRecipeResponseNetModel(
    @SerialName("hits") val hits: List<EdamamHitNetModel>? = null,
    @SerialName("_links") val links: EdamamLinksNetModel? = null,
)
