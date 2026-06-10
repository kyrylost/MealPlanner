package dev.stukalo.mealplanner.data.network.openfoodfacts.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OFFNutrimentsNetModel(
    @SerialName("energy-kcal_100g") val energyKcal100g: Double? = null,
    @SerialName("proteins_100g") val proteins100g: Double? = null,
    @SerialName("carbohydrates_100g") val carbohydrates100g: Double? = null,
    @SerialName("fat_100g") val fat100g: Double? = null,
    @SerialName("energy-kcal") val energyKcal: Double? = null,
    @SerialName("proteins") val proteins: Double? = null,
    @SerialName("carbohydrates") val carbohydrates: Double? = null,
    @SerialName("fat") val fat: Double? = null,
)
