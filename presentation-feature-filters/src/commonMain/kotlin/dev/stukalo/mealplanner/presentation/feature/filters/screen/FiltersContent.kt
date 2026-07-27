package dev.stukalo.mealplanner.presentation.feature.filters.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_apply
import dev.stukalo.mealplanner.core.localization.common_calories
import dev.stukalo.mealplanner.core.localization.common_carbs
import dev.stukalo.mealplanner.core.localization.common_fats
import dev.stukalo.mealplanner.core.localization.common_filters
import dev.stukalo.mealplanner.core.localization.common_meal_types
import dev.stukalo.mealplanner.core.localization.common_proteins
import dev.stukalo.mealplanner.core.localization.common_unit_grams
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.feature.filters.screen.component.MealTypeSelector
import dev.stukalo.mealplanner.presentation.feature.filters.screen.component.RangeInput
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FiltersContent(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit
) {
    val filters = state.filters

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.common_filters)) },
                navigationIcon = {
                    IconButton(onClick = { onIntent(ViewIntent.OnBackClick) }) {
                        Text("←")
                    }
                },
                actions = {
                    TextButton(onClick = { onIntent(ViewIntent.OnApplyClick) }) {
                        Text(stringResource(Res.string.common_apply))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(stringResource(Res.string.common_calories), style = MaterialTheme.typography.titleMedium)
            RangeInput(
                min = filters.minCalories,
                max = filters.maxCalories,
                onMinChange = { onIntent(ViewIntent.OnMinCaloriesChange(it)) },
                onMaxChange = { onIntent(ViewIntent.OnMaxCaloriesChange(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${stringResource(Res.string.common_proteins)} (${stringResource(Res.string.common_unit_grams)})",
                style = MaterialTheme.typography.titleMedium
            )
            RangeInput(
                min = filters.minProteins,
                max = filters.maxProteins,
                onMinChange = { onIntent(ViewIntent.OnMinProteinsChange(it)) },
                onMaxChange = { onIntent(ViewIntent.OnMaxProteinsChange(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${stringResource(Res.string.common_fats)} (${stringResource(Res.string.common_unit_grams)})",
                style = MaterialTheme.typography.titleMedium
            )
            RangeInput(
                min = filters.minFats,
                max = filters.maxFats,
                onMinChange = { onIntent(ViewIntent.OnMinFatsChange(it)) },
                onMaxChange = { onIntent(ViewIntent.OnMaxFatsChange(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${stringResource(Res.string.common_carbs)} (${stringResource(Res.string.common_unit_grams)})",
                style = MaterialTheme.typography.titleMedium
            )
            RangeInput(
                min = filters.minCarbs,
                max = filters.maxCarbs,
                onMinChange = { onIntent(ViewIntent.OnMinCarbsChange(it)) },
                onMaxChange = { onIntent(ViewIntent.OnMaxCarbsChange(it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(Res.string.common_meal_types), style = MaterialTheme.typography.titleMedium)
            MealTypeSelector(
                selectedTypes = filters.mealTypes,
                onToggleType = { onIntent(ViewIntent.OnToggleMealType(it)) }
            )
        }
    }
}

@Preview
@Composable
private fun FiltersContentPreview() {
    Theme {
        FiltersContent(
            state = ViewState(),
            onIntent = {}
        )
    }
}
