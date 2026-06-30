package dev.stukalo.mealplanner.presentation.feature.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.hazeSource
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState

@Composable
internal fun BackgroundCircles(
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    val primaryColor = Theme.color.secondary
    val backgroundColor = Theme.color.background
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .hazeSource(hazeState)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(primaryColor.copy(alpha = 0.2f), Color.Transparent),
                    center = Offset(size.width * 0.3f, size.height * 0.25f),
                    radius = 192.dp.toPx()
                ),
                radius = 192.dp.toPx(),
                center = Offset(size.width * 0.3f, size.height * 0.25f)
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(primaryColor.copy(alpha = 0.1f), Color.Transparent),
                    center = Offset(size.width * 0.8f, size.height * 0.7f),
                    radius = 96.dp.toPx()
                ),
                radius = 96.dp.toPx(),
                center = Offset(size.width * 0.8f, size.height * 0.7f)
            )
        }
    }
}

@Preview
@Composable
private fun BackgroundCirclesPreview() {
    Theme {
        BackgroundCircles(hazeState = rememberHazeState())
    }
}
