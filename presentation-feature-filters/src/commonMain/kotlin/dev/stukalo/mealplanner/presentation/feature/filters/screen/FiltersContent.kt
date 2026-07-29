package dev.stukalo.mealplanner.presentation.feature.filters.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
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
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBack
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.PrimaryButton
import dev.stukalo.mealplanner.presentation.core.ui.widget.header.CommonHeader
import dev.stukalo.mealplanner.presentation.feature.filters.screen.component.MealTypeSelector
import dev.stukalo.mealplanner.presentation.feature.filters.screen.component.RangeInput
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.filters.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun FiltersContent(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit
) {
    val filters = state.filters

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CommonHeader(
                title = stringResource(Res.string.common_filters),
                leftIcon = IconBack,
                onLeftIconClick = { onIntent(ViewIntent.OnBackClick) }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = Theme.spacing.space16,
                    end = Theme.spacing.space16,
                    top = Theme.spacing.space16,
                    bottom = Theme.spacing.space128
                ),
                verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
            ) {
                item {
                    FilterSection(title = stringResource(Res.string.common_calories)) {
                        RangeInput(
                            min = filters.minCalories,
                            max = filters.maxCalories,
                            onMinChange = { onIntent(ViewIntent.OnMinCaloriesChange(it)) },
                            onMaxChange = { onIntent(ViewIntent.OnMaxCaloriesChange(it)) }
                        )
                    }
                }

                item {
                    FilterSection(
                        title = "${stringResource(Res.string.common_proteins)} (${stringResource(Res.string.common_unit_grams)})"
                    ) {
                        RangeInput(
                            min = filters.minProteins,
                            max = filters.maxProteins,
                            onMinChange = { onIntent(ViewIntent.OnMinProteinsChange(it)) },
                            onMaxChange = { onIntent(ViewIntent.OnMaxProteinsChange(it)) }
                        )
                    }
                }

                item {
                    FilterSection(
                        title = "${stringResource(Res.string.common_fats)} (${stringResource(Res.string.common_unit_grams)})"
                    ) {
                        RangeInput(
                            min = filters.minFats,
                            max = filters.maxFats,
                            onMinChange = { onIntent(ViewIntent.OnMinFatsChange(it)) },
                            onMaxChange = { onIntent(ViewIntent.OnMaxFatsChange(it)) }
                        )
                    }
                }

                item {
                    FilterSection(
                        title = "${stringResource(Res.string.common_carbs)} (${stringResource(Res.string.common_unit_grams)})"
                    ) {
                        RangeInput(
                            min = filters.minCarbs,
                            max = filters.maxCarbs,
                            onMinChange = { onIntent(ViewIntent.OnMinCarbsChange(it)) },
                            onMaxChange = { onIntent(ViewIntent.OnMaxCarbsChange(it)) }
                        )
                    }
                }

                item {
                    FilterSection(title = stringResource(Res.string.common_meal_types)) {
                        MealTypeSelector(
                            selectedTypes = filters.mealTypes,
                            onToggleType = { onIntent(ViewIntent.OnToggleMealType(it)) }
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(Theme.spacing.space32))
                }
            }
        }

        PrimaryButton(
            text = stringResource(Res.string.common_apply),
            onClick = { onIntent(ViewIntent.OnApplyClick) },
            corner = Theme.radius.radius24,
            textStyle = Theme.typography.bold14,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(Theme.spacing.space16)
                .navigationBarsPadding()
        )
    }
}

@Composable
private fun FilterSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(Theme.shape.normalRoundedCornerShape)
            .background(Theme.color.backgroundSecondary.copy(alpha = 0.5f))
            .padding(Theme.spacing.space16)
    ) {
        Text(
            text = title,
            style = Theme.typography.bold16,
            color = Theme.color.textPrimary
        )
        Spacer(modifier = Modifier.height(Theme.spacing.space12))
        content()
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
