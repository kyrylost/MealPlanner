package dev.stukalo.mealplanner.presentation.core.ui.widget.progress

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun LinearScaleProgressBar(
    progress: Float,
    progressColor: Color = Theme.color.primary,
    guidelineColor: Color = Theme.color.surfaceVariant,
    labelColor: Color = Theme.color.textPrimary,
    topLeftLabel: String? = null,
    topRightLabel: String? = null,
    bottomLeftLabel: String? = null,
    bottomRightLabel: String? = null,
    topLeftStyle: TextStyle = Theme.typography.bold16,
    topRightStyle: TextStyle = Theme.typography.regular12,
    bottomLeftStyle: TextStyle = Theme.typography.regular12,
    bottomRightStyle: TextStyle = Theme.typography.regular12,
    topPadding: Dp = Theme.spacing.space12,
    bottomPadding: Dp = Theme.spacing.space8,
    progressIndicatorWidth: Dp = 8.dp,
    progressIndicatorHeight: Dp = 24.dp,
    modifier: Modifier = Modifier,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (progress.isNaN()) 0f else progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 1000),
        label = "progressAnimation"
    )

    val lineThickness = 1.dp
    val verticalPadding = 1.dp

    Column(modifier = modifier) {
        // Top Labels
        if (topLeftLabel != null || topRightLabel != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (topLeftLabel != null) {
                    Text(
                        text = topLeftLabel,
                        style = topLeftStyle,
                        color = labelColor
                    )
                }
                if (topRightLabel != null) {
                    Text(
                        text = topRightLabel,
                        style = topRightStyle,
                        color = labelColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(topPadding))
        }

        // Progress Scale and Indicator
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(progressIndicatorHeight + (progressIndicatorHeight / 2) + 4.dp)
        ) {
            val width = size.width
            val barHeightPx = progressIndicatorHeight.toPx()
            val lineThicknessPx = lineThickness.toPx()
            val progressX = width * animatedProgress
            val verticalPaddingPx = verticalPadding.toPx()

            // Draw Top and Bottom boundary lines (1dp)
            drawLine(
                color = guidelineColor,
                start = Offset(0f, 0f),
                end = Offset(width, 0f),
                strokeWidth = lineThicknessPx
            )
            drawLine(
                color = guidelineColor,
                start = Offset(0f, barHeightPx),
                end = Offset(width, barHeightPx),
                strokeWidth = lineThicknessPx
            )

            // Draw Reversed Gradient fill behind the indicator
            if (progressX > 0) {
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            progressColor.copy(alpha = 0.25f)
                        ),
                        startX = 0f,
                        endX = progressX
                    ),
                    topLeft = Offset(0f, verticalPaddingPx),
                    size = Size(progressX, barHeightPx - 2 * verticalPaddingPx)
                )
            }

            // Draw Progress Indicator (Rectangle with 1dp padding from lines)
            drawLine(
                color = progressColor,
                start = Offset(progressX, verticalPaddingPx),
                end = Offset(progressX, barHeightPx - verticalPaddingPx),
                strokeWidth = progressIndicatorWidth.toPx(),
                cap = StrokeCap.Butt
            )

            // Draw Ticks every 10% under the bar (height is half of bar height)
            val tickCount = 11
            val tickHeightPx = barHeightPx / 2
            val tickYStart = barHeightPx + 4.dp.toPx()
            val tickYEnd = tickYStart + tickHeightPx

            for (i in 0 until tickCount) {
                val x = (width / (tickCount - 1)) * i
                drawLine(
                    color = guidelineColor,
                    start = Offset(x, tickYStart),
                    end = Offset(x, tickYEnd),
                    strokeWidth = lineThicknessPx
                )
            }
        }

        // Bottom Labels
        if (bottomLeftLabel != null || bottomRightLabel != null) {
            Spacer(modifier = Modifier.height(bottomPadding))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (bottomLeftLabel != null) {
                    Text(
                        text = bottomLeftLabel,
                        style = bottomLeftStyle,
                        color = labelColor
                    )
                }
                if (bottomRightLabel != null) {
                    Text(
                        text = bottomRightLabel,
                        style = bottomRightStyle,
                        color = labelColor
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LinearScaleProgressBarPreview() {
    Theme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(24.dp)
        ) {
            LinearScaleProgressBar(
                progress = 0.83f,
                topLeftLabel = "Nutrition",
                topRightLabel = "83%",
                bottomLeftLabel = "0",
                bottomRightLabel = "162 kcal"
            )
        }
    }
}
