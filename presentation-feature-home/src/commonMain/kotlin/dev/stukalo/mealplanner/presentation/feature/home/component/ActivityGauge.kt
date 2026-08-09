package dev.stukalo.mealplanner.presentation.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.core.ui.widget.card.BlurredCard
import dev.stukalo.mealplanner.presentation.core.ui.widget.progress.circular.LabelPosition
import dev.stukalo.mealplanner.presentation.core.ui.widget.progress.circular.SemiCircularProgressBar

@Composable
internal fun ActivityGauge(
    current: Float,
    target: Float,
    label: String,
    unit: String,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val safeProgress = if (target > 0) (current / target).coerceIn(0f, 1f) else 0f
    val percentage = if (target > 0) (current / target * 100).toInt() else 0

    BlurredCard(
        modifier =
        modifier
            .clip(RoundedCornerShape(Theme.radius.radius20))
            .clickable { onClick() },
        hazeState = hazeState
    ) {
        SemiCircularProgressBar(
            progress = safeProgress,
            progressBrush =
            Brush.linearGradient(
                colors =
                listOf(
                    Theme.color.brand.secondary,
                    Theme.color.brand.primary
                )
            ),
            thickness = Theme.thickness.thickness4,
            centerTitle = "$percentage%",
            centerSubtitle = label,
            leftTitle = "${current.toInt()}",
            rightTitle = "${target.toInt()} $unit",
            centerTitleStyle = Theme.typography.bold16,
            sideTitleStyle = Theme.typography.regular12,
            labelPadding = Theme.spacing.space12,
            labelPosition = LabelPosition.Bottom,
            modifier =
            Modifier
                .fillMaxSize()
                .padding(Theme.spacing.space12)
        )
    }
}

@Preview
@Composable
private fun ActivityGaugePreview() {
    Theme {
        ActivityGauge(
            current = 45f,
            target = 60f,
            label = "Proteins",
            unit = "g",
            hazeState = rememberHazeState()
        )
    }
}
