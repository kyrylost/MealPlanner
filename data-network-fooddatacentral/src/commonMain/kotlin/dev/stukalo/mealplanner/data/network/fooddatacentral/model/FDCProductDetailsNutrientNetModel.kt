package dev.stukalo.mealplanner.data.network.fooddatacentral.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FDCProductDetailsNutrientNetModel(
    @SerialName("type") val type: String? = null,
    @SerialName("id") val id: Long? = null,
    @SerialName("amount") val amount: Double? = null,
    @SerialName("nutrient") val nutrient: FDCProductDetailsNutrientInfoNetModel? = null
)
