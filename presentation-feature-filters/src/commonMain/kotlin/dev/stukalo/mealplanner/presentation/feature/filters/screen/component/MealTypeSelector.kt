package dev.stukalo.mealplanner.presentation.feature.filters.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MealTypeSelector(
    selectedTypes: List<MealTypeDomainModel>,
    onToggleType: (MealTypeDomainModel) -> Unit,
    modifier: Modifier = Modifier,
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
                        text = type.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = if (isSelected) Theme.typography.bold14 else Theme.typography.regular14
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Theme.color.primary,
                    selectedLabelColor = Theme.color.textOnPrimary,
                    labelColor = Theme.color.textPrimary,
                    containerColor = Theme.color.backgroundSecondary.copy(alpha = 0.5f)
                ),
                border = null
            )
        }
    }
}
