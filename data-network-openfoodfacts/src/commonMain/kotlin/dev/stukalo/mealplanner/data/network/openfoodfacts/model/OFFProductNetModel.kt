package dev.stukalo.mealplanner.data.network.openfoodfacts.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Network data model representing a product from the Open Food Facts API.
 *
 * @property code The barcode or unique identifier of the product.
 * @property imageUrl URL to the main product image.
 * @property nutriments Detailed nutritional data.
 * @property nutritionData Raw nutrition data string.
 * @property nutritionDataPer Reference unit for nutrition data (e.g., "100g").
 * @property nutritionDataPreparedPer Reference unit for prepared nutrition data.
 * @property productName The common name of the product.
 * @property brands Brand name(s) associated with the product.
 * @property ingredientsText Full text of the ingredients list.
 * @property servingSize Suggested serving size string.
 * @property nutriScoreGrade Nutri-Score grade (A-E).
 * @property novaGroup NOVA processing group (1-4).
 */
@Serializable
data class OFFProductNetModel(
    @SerialName("code") val code: String? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("nutriments") val nutriments: OFFNutrimentsNetModel? = null,
    @SerialName("nutrition_data") val nutritionData: String? = null,
    @SerialName("nutrition_data_per") val nutritionDataPer: String? = null,
    @SerialName("nutrition_data_prepared_per") val nutritionDataPreparedPer: String? = null,
    @SerialName("product_name") val productName: String? = null,
    @SerialName("brands") val brands: String? = null,
    @SerialName("ingredients_text") val ingredientsText: String? = null,
    @SerialName("serving_size") val servingSize: String? = null,
    @SerialName("nutriscore_grade") val nutriScoreGrade: String? = null,
    @SerialName("nova_group") val novaGroup: Int? = null
)
