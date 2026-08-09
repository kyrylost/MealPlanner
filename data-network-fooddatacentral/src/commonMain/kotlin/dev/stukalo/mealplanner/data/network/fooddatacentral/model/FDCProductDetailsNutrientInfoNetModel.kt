package dev.stukalo.mealplanner.data.network.fooddatacentral.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FDCProductDetailsNutrientInfoNetModel(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("unitName") val unitName: String
)
