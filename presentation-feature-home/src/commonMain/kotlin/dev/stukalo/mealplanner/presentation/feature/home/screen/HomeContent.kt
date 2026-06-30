package dev.stukalo.mealplanner.presentation.feature.home.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.home_carbs
import dev.stukalo.mealplanner.core.localization.home_fats
import dev.stukalo.mealplanner.core.localization.home_hello
import dev.stukalo.mealplanner.core.localization.home_proteins
import dev.stukalo.mealplanner.core.localization.home_recommended_for_today
import dev.stukalo.mealplanner.core.localization.home_steps
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalBottomBarHeight
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.widget.card.BlurredCard
import dev.stukalo.mealplanner.presentation.core.ui.widget.picker.ValueEditDialog
import dev.stukalo.mealplanner.presentation.feature.home.component.ActivityGauge
import dev.stukalo.mealplanner.presentation.feature.home.component.BackgroundCircles
import dev.stukalo.mealplanner.presentation.feature.home.component.NutritionCard
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.NutrientType
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.home.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeContent(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit,
) {
    val hazeState = rememberHazeState()
    var activeNutrientType by remember { mutableStateOf<NutrientType?>(null) }

    activeNutrientType?.let { type ->
        val label = when (type) {
            NutrientType.PROTEINS -> stringResource(Res.string.home_proteins)
            NutrientType.FATS -> stringResource(Res.string.home_fats)
            NutrientType.CARBS -> stringResource(Res.string.home_carbs)
        }
        ValueEditDialog(
            initialValue = "",
            onDismissRequest = { activeNutrientType = null },
            onConfirm = { amount ->
                amount.toFloatOrNull()?.let {
                    onIntent(ViewIntent.OnAddNutrient(type, it))
                }
            },
            title = label,
            placeholder = "0.0",
            confirmLabel = stringResource(Res.string.common_ok),
            dismissLabel = stringResource(Res.string.common_cancel)
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundCircles(hazeState)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = Theme.spacing.space16)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(Theme.spacing.space48))

            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = stringResource(Res.string.home_hello),
                        style = Theme.typography.regular48,
                        color = Theme.color.textSecondary
                    )
                    Text(
                        text = "${state.userName}!",
                        style = Theme.typography.semibold48,
                        color = Theme.color.textPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(Theme.spacing.space128))

            NutritionCard(
                currentCalories = state.currentCalories,
                targetCalories = state.targetCalories,
                hazeState = hazeState
            )

            Spacer(modifier = Modifier.height(Theme.spacing.space24))

            Row(modifier = Modifier.fillMaxWidth()) {
                ActivityGauge(
                    current = state.proteins,
                    target = state.proteinsTarget,
                    label = stringResource(Res.string.home_proteins),
                    unit = "g",
                    modifier = Modifier.weight(1f),
                    hazeState = hazeState,
                    onClick = { activeNutrientType = NutrientType.PROTEINS }
                )
                Spacer(modifier = Modifier.width(Theme.spacing.space16))
                ActivityGauge(
                    current = state.fats,
                    target = state.fatsTarget,
                    label = stringResource(Res.string.home_fats),
                    unit = "g",
                    modifier = Modifier.weight(1f),
                    hazeState = hazeState,
                    onClick = { activeNutrientType = NutrientType.FATS }
                )
            }
            Spacer(modifier = Modifier.height(Theme.spacing.space16))
            Row(modifier = Modifier.fillMaxWidth()) {
                ActivityGauge(
                    current = state.carbs,
                    target = state.carbsTarget,
                    label = stringResource(Res.string.home_carbs),
                    unit = "g",
                    modifier = Modifier.weight(1f),
                    hazeState = hazeState,
                    onClick = { activeNutrientType = NutrientType.CARBS }
                )
                Spacer(modifier = Modifier.width(Theme.spacing.space16))
                ActivityGauge(
                    current = state.steps,
                    target = state.stepsTarget,
                    label = stringResource(Res.string.home_steps),
                    unit = "",
                    modifier = Modifier.weight(1f),
                    hazeState = hazeState
                )
            }

            Spacer(modifier = Modifier.height(Theme.spacing.space32))

            Text(
                text = stringResource(Res.string.home_recommended_for_today),
                style = Theme.typography.bold16,
                color = Theme.color.textPrimary
            )

            Spacer(modifier = Modifier.height(Theme.spacing.space16))

            repeat(2) { index ->
                BlurredCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(bottom = Theme.spacing.space16),
                    hazeState = hazeState
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { onIntent(ViewIntent.OnRecipeClick("recipe_$index")) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Recipe Placeholder ${index + 1}",
                            style = Theme.typography.regular14,
                            color = Theme.color.textPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(LocalBottomBarHeight.current))
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    Theme {
        HomeContent(
            state = ViewState(
                userName = "Name",
                currentCalories = 1258,
                targetCalories = 2000,
                proteins = 45f,
                proteinsTarget = 60f,
                fats = 30f,
                fatsTarget = 70f,
                carbs = 150f,
                carbsTarget = 250f,
                steps = 8432f,
                stepsTarget = 10000f
            ),
            onIntent = {}
        )
    }
}
