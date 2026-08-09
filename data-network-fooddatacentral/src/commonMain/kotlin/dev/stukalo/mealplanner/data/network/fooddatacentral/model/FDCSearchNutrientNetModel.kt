package dev.stukalo.mealplanner.data.network.fooddatacentral.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FDCSearchNutrientNetModel(
    @SerialName("nutrientId") val nutrientId: Int,
    @SerialName("nutrientName") val nutrientName: String,
    @SerialName("nutrientNumber") val nutrientNumber: String,
    @SerialName("unitName") val unitName: String,
    @SerialName("value") val value: Double,
    @SerialName("percentDailyValue") val percentDailyValue: Double? = null
)
