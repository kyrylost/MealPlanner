package dev.stukalo.mealplanner.presentation.feature.recipe.filters.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.feature.recipe.common.core.mapper.toText
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun MealTypeSelector(
    selectedTypes: List<MealTypeDomainModel>,
    onToggleType: (MealTypeDomainModel) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space8)
    ) {
        MealTypeDomainModel.entries.forEach { type ->
            val isSelected = selectedTypes.contains(type)
            FilterChip(
                selected = isSelected,
                onClick = { onToggleType(type) },
                label = {
                    Text(
                        text = stringResource(type.toText()),
                        style = if (isSelected) Theme.typography.bold14 else Theme.typography.regular14
                    )
                },
                colors =
                FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Theme.color.brand.primary,
                    selectedLabelColor = Theme.color.text.onPrimary,
                    labelColor = Theme.color.text.primary,
                    containerColor = Theme.color.background.secondary.copy(alpha = 0.5f)
                ),
                border = null
            )
        }
    }
}

@Preview
@Composable
private fun MealTypeSelectorPreview() {
    Theme {
        MealTypeSelector(
            selectedTypes = listOf(MealTypeDomainModel.BREAKFAST, MealTypeDomainModel.LUNCH),
            onToggleType = {}
        )
    }
}
