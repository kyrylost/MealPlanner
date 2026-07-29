package dev.stukalo.mealplanner.presentation.feature.recipe.search.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_calories
import dev.stukalo.mealplanner.core.localization.common_carbs
import dev.stukalo.mealplanner.core.localization.common_fats
import dev.stukalo.mealplanner.core.localization.common_grams_short
import dev.stukalo.mealplanner.core.localization.common_kcal_short
import dev.stukalo.mealplanner.core.localization.common_proteins
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.filter.FilterDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconClose
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActiveFilterChips(
    filters: FilterDomainModel,
    onRemoveMealType: (MealTypeDomainModel) -> Unit,
    onRemoveNutrient: (NutrientType) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeChips = remember(filters) {
        mutableListOf<ChipData>().apply {
            filters.mealTypes.forEach { type ->
                add(ChipData.MealType(type))
            }
            if (filters.minCalories != null || filters.maxCalories != null) {
                add(ChipData.Nutrient(NutrientType.CALORIES, filters.minCalories, filters.maxCalories))
            }
            if (filters.minProteins != null || filters.maxProteins != null) {
                add(ChipData.Nutrient(NutrientType.PROTEINS, filters.minProteins, filters.maxProteins))
            }
            if (filters.minFats != null || filters.maxFats != null) {
                add(ChipData.Nutrient(NutrientType.FATS, filters.minFats, filters.maxFats))
            }
            if (filters.minCarbs != null || filters.maxCarbs != null) {
                add(ChipData.Nutrient(NutrientType.CARBS, filters.minCarbs, filters.maxCarbs))
            }
        }
    }

    if (activeChips.isEmpty()) return

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = Theme.spacing.space16),
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space8)
    ) {
        items(activeChips) { chip ->
            AssistChip(
                onClick = {
                    when (chip) {
                        is ChipData.MealType -> onRemoveMealType(chip.type)
                        is ChipData.Nutrient -> onRemoveNutrient(chip.type)
                    }
                },
                label = { Text(chip.getLabel()) },
                trailingIcon = {
                    Icon(
                        imageVector = IconClose,
                        contentDescription = null,
                        modifier = Modifier.size(Theme.spacing.space16),
                        tint = Theme.color.textSecondary
                    )
                },
                shape = Theme.shape.normalRoundedCornerShape,
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Theme.color.primary.copy(alpha = 0.1f),
                    labelColor = Theme.color.textPrimary
                ),
                border = null
            )
        }
    }
}

private sealed class ChipData {
    @Composable
    abstract fun getLabel(): String

    data class MealType(val type: MealTypeDomainModel) : ChipData() {
        @Composable
        override fun getLabel() = type.name.lowercase().replaceFirstChar { it.uppercase() }
    }

    data class Nutrient(val type: NutrientType, val min: Int?, val max: Int?) : ChipData() {
        @Composable
        override fun getLabel(): String {
            val name = stringResource(type.labelRes)
            val range = when {
                min != null && max != null -> "$min-$max"
                min != null -> ">$min"
                max != null -> "<$max"
                else -> ""
            }
            return "$name: $range ${stringResource(type.unitRes)}"
        }
    }
}

enum class NutrientType(val labelRes: StringResource, val unitRes: StringResource) {
    CALORIES(Res.string.common_calories, Res.string.common_kcal_short),
    PROTEINS(Res.string.common_proteins, Res.string.common_grams_short),
    FATS(Res.string.common_fats, Res.string.common_grams_short),
    CARBS(Res.string.common_carbs, Res.string.common_grams_short)
}
