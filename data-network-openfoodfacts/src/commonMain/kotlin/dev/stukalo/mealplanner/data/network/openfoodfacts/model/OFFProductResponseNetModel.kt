package dev.stukalo.mealplanner.data.network.openfoodfacts.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OFFProductResponseNetModel(
    @SerialName("code") val code: String? = null,
    @SerialName("product") val product: OFFProductNetModel? = null,
    @SerialName("status") val status: Int? = null,
    @SerialName("status_verbose") val statusVerbose: String? = null
)
