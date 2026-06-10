package dev.stukalo.mealplanner.data.network.openfoodfacts.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OFFProductNetModel(
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("nutriments") val nutriments: OFFNutrimentsNetModel? = null,
    @SerialName("nutrition_data") val nutritionData: String? = null,
    @SerialName("nutrition_data_per") val nutritionDataPer: String? = null,
    @SerialName("nutrition_data_prepared_per") val nutritionDataPreparedPer: String? = null,
    @SerialName("product_name") val productName: String? = null,
)
