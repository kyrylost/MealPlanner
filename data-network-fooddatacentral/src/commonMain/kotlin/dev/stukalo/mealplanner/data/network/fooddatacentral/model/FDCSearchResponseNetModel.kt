package dev.stukalo.mealplanner.data.network.fooddatacentral.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FDCSearchResponseNetModel(
    @SerialName("totalHits") val totalHits: Int? = null,
    @SerialName("currentPage") val currentPage: Int? = null,
    @SerialName("totalPages") val totalPages: Int? = null,
    @SerialName("foods") val foods: List<FDCSearchProductNetModel>? = null
)
