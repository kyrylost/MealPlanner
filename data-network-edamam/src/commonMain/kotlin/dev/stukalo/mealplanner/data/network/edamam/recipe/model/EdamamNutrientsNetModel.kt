package dev.stukalo.mealplanner.data.network.edamam.recipe.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EdamamNutrientsNetModel(
    @SerialName("PROCNT") val protein: EdamamNutrientNetModel? = null,
    @SerialName("FAT") val fat: EdamamNutrientNetModel? = null,
    @SerialName("CHOCDF") val carbs: EdamamNutrientNetModel? = null,
    @SerialName("ENERC_KCAL") val energy: EdamamNutrientNetModel? = null,
    @SerialName("FASAT") val fatSaturated: EdamamNutrientNetModel? = null,
    @SerialName("FATRN") val fatTrans: EdamamNutrientNetModel? = null,
    @SerialName("FAMS") val fatMono: EdamamNutrientNetModel? = null,
    @SerialName("FAPU") val fatPoly: EdamamNutrientNetModel? = null,
    @SerialName("FIBTG") val fiber: EdamamNutrientNetModel? = null,
    @SerialName("SUGAR") val sugar: EdamamNutrientNetModel? = null,
    @SerialName("CHOLE") val cholesterol: EdamamNutrientNetModel? = null,
    @SerialName("NA") val sodium: EdamamNutrientNetModel? = null,
    @SerialName("CA") val calcium: EdamamNutrientNetModel? = null,
    @SerialName("MG") val magnesium: EdamamNutrientNetModel? = null,
    @SerialName("K") val potassium: EdamamNutrientNetModel? = null,
    @SerialName("FE") val iron: EdamamNutrientNetModel? = null,
    @SerialName("ZN") val zinc: EdamamNutrientNetModel? = null,
    @SerialName("P") val phosphorus: EdamamNutrientNetModel? = null,
    @SerialName("VITA_RAE") val vitA: EdamamNutrientNetModel? = null,
    @SerialName("VITC") val vitC: EdamamNutrientNetModel? = null,
    @SerialName("VITB6A") val vitB6: EdamamNutrientNetModel? = null,
    @SerialName("VITB12") val vitB12: EdamamNutrientNetModel? = null,
    @SerialName("VITD") val vitD: EdamamNutrientNetModel? = null,
    @SerialName("TOCPHA") val vitE: EdamamNutrientNetModel? = null,
    @SerialName("VITK1") val vitK: EdamamNutrientNetModel? = null
)
