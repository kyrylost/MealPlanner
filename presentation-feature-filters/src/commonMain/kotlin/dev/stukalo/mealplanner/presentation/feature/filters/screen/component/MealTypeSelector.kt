package dev.stukalo.mealplanner.presentation.feature.filters.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MealTypeSelector(
    selectedTypes: List<MealTypeDomainModel>,
    onToggleType: (MealTypeDomainModel) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MealTypeDomainModel.entries.forEach { type ->
            FilterChip(
                selected = selectedTypes.contains(type),
                onClick = { onToggleType(type) },
                label = { Text(type.name.lowercase().replaceFirstChar { it.uppercase() }) }
            )
        }
    }
}
