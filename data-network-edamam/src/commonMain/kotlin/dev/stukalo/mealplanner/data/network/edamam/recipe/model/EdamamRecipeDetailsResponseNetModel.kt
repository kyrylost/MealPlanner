package dev.stukalo.mealplanner.data.network.edamam.recipe.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EdamamRecipeDetailsResponseNetModel(
    @SerialName("recipe") val recipe: EdamamRecipeNetModel? = null,
    @SerialName("_links") val links: EdamamLinksNetModel? = null,
)
