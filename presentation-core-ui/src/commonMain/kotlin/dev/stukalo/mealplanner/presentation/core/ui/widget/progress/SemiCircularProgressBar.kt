package dev.stukalo.mealplanner.presentation.core.ui.widget.progress

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun SemiCircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = Theme.color.backgroundSecondary.copy(alpha = 0.3f),
    progressColor: Color = Theme.color.primary,
    centerTitle: String,
    centerSubtitle: String,
    leftTitle: String? = null,
    leftSubtitle: String? = null,
    rightTitle: String? = null,
    rightSubtitle: String? = null,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000)
    )

    Box(modifier = modifier) {
        // Left Side Text
        if (leftTitle != null || leftSubtitle != null) {
            Column(
                modifier = Modifier.align(Alignment.TopStart),
                horizontalAlignment = Alignment.Start
            ) {
                if (leftTitle != null) {
                    Text(
                        text = leftTitle,
                        style = Theme.typography.bold16.copy(
                            color = Theme.color.textPrimary
                        )
                    )
                }
                if (leftSubtitle != null) {
                    Text(
                        text = leftSubtitle,
                        style = Theme.typography.regular12.copy(
                            color = Theme.color.textSecondary
                        )
                    )
                }
            }
        }

        // Right Side Text
        if (rightTitle != null || rightSubtitle != null) {
            Column(
                modifier = Modifier.align(Alignment.TopEnd),
                horizontalAlignment = Alignment.End
            ) {
                if (rightTitle != null) {
                    Text(
                        text = rightTitle,
                        style = Theme.typography.bold16.copy(
                            color = Theme.color.textPrimary
                        )
                    )
                }
                if (rightSubtitle != null) {
                    Text(
                        text = rightSubtitle,
                        style = Theme.typography.regular12.copy(
                            color = Theme.color.textSecondary
                        )
                    )
                }
            }
        }

        // Progress Arc
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .align(Alignment.BottomCenter)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 24.dp.toPx()
                val diameter = size.width - strokeWidth
                val arcSize = Size(diameter, diameter)
                val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)

                // Track
                drawArc(
                    color = trackColor,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    size = arcSize,
                    topLeft = topLeft
                )

                // Progress
                drawArc(
                    color = progressColor,
                    startAngle = 180f,
                    sweepAngle = 180f * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                    size = arcSize,
                    topLeft = topLeft
                )
            }

            // Center Text
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = centerTitle,
                    style = Theme.typography.semibold48.copy(
                        color = Theme.color.textPrimary
                    )
                )
                Text(
                    text = centerSubtitle,
                    style = Theme.typography.regular12.copy(
                        color = Theme.color.textSecondary
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun SemiCircularProgressBarPreview() {
    Theme {
        Box(modifier = Modifier.background(Theme.color.iconDisabled).padding(32.dp)) {
            SemiCircularProgressBar(
                progress = 0.5f,
                centerTitle = "180",
                centerSubtitle = "Kkal left",
                leftTitle = "1360",
                leftSubtitle = "Eaten",
                rightTitle = "1860",
                rightSubtitle = "Burned",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
