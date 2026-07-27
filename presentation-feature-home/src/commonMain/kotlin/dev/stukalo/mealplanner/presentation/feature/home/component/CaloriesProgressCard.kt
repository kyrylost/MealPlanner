package dev.stukalo.mealplanner.presentation.feature.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_calories
import dev.stukalo.mealplanner.core.localization.common_kcal
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.widget.card.BlurredCard
import dev.stukalo.mealplanner.presentation.core.ui.widget.progress.LinearScaleProgressBar
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CaloriesProgressCard(
    currentCalories: Int,
    targetCalories: Int,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    val progress = if (targetCalories > 0) (currentCalories.toFloat() / targetCalories).coerceIn(0f, 1f) else 0f
    val percentage = "${(progress * 100).toInt()}%"

    BlurredCard(
        modifier = modifier.fillMaxWidth(),
        hazeState = hazeState,
        shape = Theme.shape.largeRoundedCornerShape
    ) {
        LinearScaleProgressBar(
            progress = progress,
            topLeftLabel = stringResource(Res.string.common_calories),
            topRightLabel = percentage,
            bottomLeftLabel = "0",
            bottomRightLabel = stringResource(Res.string.common_kcal, targetCalories),
            topPadding = Theme.spacing.space16,
            bottomPadding = Theme.spacing.space12,
            modifier = Modifier.padding(Theme.spacing.space16),
        )
    }
}

@Preview
@Composable
private fun CaloriesProgressCardPreview() {
    Theme {
        CaloriesProgressCard(
            currentCalories = 1258,
            targetCalories = 2000,
            hazeState = rememberHazeState()
        )
    }
}
