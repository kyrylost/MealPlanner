package dev.stukalo.mealplanner.presentation.feature.recipe.common.core.mapper

import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_breakfast
import dev.stukalo.mealplanner.core.localization.common_dinner
import dev.stukalo.mealplanner.core.localization.common_lunch
import dev.stukalo.mealplanner.core.localization.common_snack
import dev.stukalo.mealplanner.core.localization.common_teatime
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import org.jetbrains.compose.resources.StringResource

/**
 * Maps [MealTypeDomainModel] to its corresponding localized string resource.
 *
 * @receiver The meal type to map.
 * @return The [StringResource] for the localized name.
 */
fun MealTypeDomainModel.toText(): StringResource = when (this) {
    MealTypeDomainModel.BREAKFAST -> Res.string.common_breakfast
    MealTypeDomainModel.LUNCH -> Res.string.common_lunch
    MealTypeDomainModel.DINNER -> Res.string.common_dinner
    MealTypeDomainModel.SNACK -> Res.string.common_snack
    MealTypeDomainModel.TEATIME -> Res.string.common_teatime
}
