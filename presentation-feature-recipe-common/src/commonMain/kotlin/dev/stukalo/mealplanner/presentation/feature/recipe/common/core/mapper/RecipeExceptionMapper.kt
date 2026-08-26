package dev.stukalo.mealplanner.presentation.feature.recipe.common.core.mapper

import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.recipe_details_not_found
import dev.stukalo.mealplanner.domain.model.exception.RecipeException
import org.jetbrains.compose.resources.StringResource

/**
 * Maps [RecipeException] to a localized [StringResource].
 */
fun RecipeException.toMessage(): StringResource = when (this) {
    is RecipeException.RecipeNotFound -> Res.string.recipe_details_not_found
}
