package dev.stukalo.mealplanner.data.network.edamam.recipe.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EdamamRecipeNetModel(
    @SerialName("uri") val uri: String? = null,
    @SerialName("label") val label: String? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("yield") val yield: Double? = null,
    @SerialName("totalNutrients") val totalNutrients: EdamamNutrientsNetModel? = null,
    @SerialName("calories") val calories: Double? = null,
    @SerialName("totalWeight") val totalWeight: Double? = null,
    @SerialName("ingredientLines") val ingredientLines: List<String>? = null,
    @SerialName("ingredients") val ingredients: List<EdamamIngredientNetModel>? = null,
    @SerialName("totalTime") val totalTime: Double? = null,
    @SerialName("cuisineType") val cuisineType: List<String>? = null,
    @SerialName("mealType") val mealType: List<String>? = null,
    @SerialName("dishType") val dishType: List<String>? = null,
    @SerialName("dietLabels") val dietLabels: List<String>? = null,
    @SerialName("healthLabels") val healthLabels: List<String>? = null,
    @SerialName("instructionLines") val instructionLines: List<String>? = null
)
