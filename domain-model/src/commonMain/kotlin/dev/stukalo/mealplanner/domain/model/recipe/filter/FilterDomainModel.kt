package dev.stukalo.mealplanner.domain.model.recipe.filter

import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class FilterDomainModel(
    val minCalories: Int? = null,
    val maxCalories: Int? = null,
    val minProteins: Int? = null,
    val maxProteins: Int? = null,
    val minFats: Int? = null,
    val maxFats: Int? = null,
    val minCarbs: Int? = null,
    val maxCarbs: Int? = null,
    val mealTypes: List<MealTypeDomainModel> = emptyList()
) {
    val caloriesRange: IntRange? get() =
        if (minCalories != null &&
            maxCalories != null
        ) {
            minCalories..maxCalories
        } else {
            null
        }
    val proteinsRange: IntRange? get() =
        if (minProteins != null &&
            maxProteins != null
        ) {
            minProteins..maxProteins
        } else {
            null
        }
    val fatsRange: IntRange? get() = if (minFats != null &&
        maxFats != null
    ) {
        minFats..maxFats
    } else {
        null
    }
    val carbsRange: IntRange? get() = if (minCarbs != null &&
        maxCarbs != null
    ) {
        minCarbs..maxCarbs
    } else {
        null
    }
}
