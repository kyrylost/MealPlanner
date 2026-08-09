package dev.stukalo.mealplanner.data.network.fooddatacentral.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FDCSearchProductNetModel(
    @SerialName("fdcId") val fdcId: Long,
    @SerialName("description") val description: String,
    @SerialName("dataType") val dataType: String? = null,
    @SerialName("brandOwner") val brandOwner: String? = null,
    @SerialName("brandName") val brandName: String? = null,
    @SerialName("gtinUpc") val gtinUpc: String? = null,
    @SerialName("ingredients") val ingredients: String? = null,
    @SerialName("foodCategory") val foodCategory: String? = null,
    @SerialName("servingSize") val servingSize: Double? = null,
    @SerialName("servingSizeUnit") val servingSizeUnit: String? = null,
    @SerialName("foodNutrients") val foodNutrients: List<FDCSearchNutrientNetModel>? = null
)
