package dev.stukalo.mealplanner.presentation.core.ui.widget.progress.circular

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun SemiCircularProgressBar(
    progress: Float,
    trackColor: Color = Theme.color.backgroundSecondary,
    progressColor: Color = Theme.color.primary,
    progressBrush: Brush? = null,
    thickness: Dp = Theme.thickness.thickness4,
    centerTitle: String,
    centerSubtitle: String,
    leftTitle: String? = null,
    leftSubtitle: String? = null,
    rightTitle: String? = null,
    rightSubtitle: String? = null,
    centerTitleStyle: TextStyle = Theme.typography.semibold48,
    centerSubtitleStyle: TextStyle = Theme.typography.regular12,
    sideTitleStyle: TextStyle = Theme.typography.bold16,
    sideSubtitleStyle: TextStyle = Theme.typography.regular12,
    centerTitleColor: Color = Theme.color.textPrimary,
    centerSubtitleColor: Color = Theme.color.textSecondary,
    sideTitleColor: Color = Theme.color.textPrimary,
    sideSubtitleColor: Color = Theme.color.textSecondary,
    labelPadding: Dp = 0.dp,
    labelPosition: LabelPosition = LabelPosition.Top,
    modifier: Modifier = Modifier,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (progress.isNaN()) 0f else progress,
        animationSpec = tween(durationMillis = 1000)
    )

    Column(modifier = modifier) {
        val horizontalPadding = (thickness.value / 2).dp

        // Progress Arc Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
        ) {
            // Top Labels (if position is Top)
            if (labelPosition == LabelPosition.Top) {
                // Left Side Text
                if (leftTitle != null || leftSubtitle != null) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = horizontalPadding + labelPadding, top = labelPadding),
                        horizontalAlignment = Alignment.Start
                    ) {
                        if (leftTitle != null) {
                            Text(
                                text = leftTitle,
                                style = sideTitleStyle,
                                color = sideTitleColor
                            )
                        }
                        if (leftSubtitle != null) {
                            Text(
                                text = leftSubtitle,
                                style = sideSubtitleStyle,
                                color = sideSubtitleColor
                            )
                        }
                    }
                }

                // Right Side Text
                if (rightTitle != null || rightSubtitle != null) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = horizontalPadding + labelPadding, top = labelPadding),
                        horizontalAlignment = Alignment.End
                    ) {
                        if (rightTitle != null) {
                            Text(
                                text = rightTitle,
                                style = sideTitleStyle,
                                color = sideTitleColor
                            )
                        }
                        if (rightSubtitle != null) {
                            Text(
                                text = rightSubtitle,
                                style = sideSubtitleStyle,
                                color = sideSubtitleColor
                            )
                        }
                    }
                }
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidthPx = thickness.toPx()
                val diameter = size.width - strokeWidthPx
                val arcSize = Size(diameter, diameter)
                val topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2)

                // Track
                drawArc(
                    color = trackColor,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                    size = arcSize,
                    topLeft = topLeft
                )

                // Progress
                drawArc(
                    brush = progressBrush ?: SolidColor(progressColor),
                    startAngle = 180f,
                    sweepAngle = 180f * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round),
                    size = arcSize,
                    topLeft = topLeft
                )
            }

            // Center Text
            Column(
                modifier = Modifier
                    .padding(top = thickness)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = centerTitle,
                    style = centerTitleStyle,
                    color = centerTitleColor
                )
                Text(
                    text = centerSubtitle,
                    style = centerSubtitleStyle,
                    color = centerSubtitleColor
                )
            }
        }

        // Bottom Labels (if position is Bottom)
        if (labelPosition == LabelPosition.Bottom && (leftTitle != null || leftSubtitle != null || rightTitle != null || rightSubtitle != null)) {
            Spacer(modifier = Modifier.height(labelPadding))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Left Side Text
                Column(horizontalAlignment = Alignment.Start) {
                    if (leftTitle != null) {
                        Text(
                            text = leftTitle,
                            style = sideTitleStyle,
                            color = sideTitleColor
                        )
                    }
                    if (leftSubtitle != null) {
                        Text(
                            text = leftSubtitle,
                            style = sideSubtitleStyle,
                            color = sideSubtitleColor
                        )
                    }
                }

                // Right Side Text
                Column(horizontalAlignment = Alignment.End) {
                    if (rightTitle != null) {
                        Text(
                            text = rightTitle,
                            style = sideTitleStyle,
                            color = sideTitleColor
                        )
                    }
                    if (rightSubtitle != null) {
                        Text(
                            text = rightSubtitle,
                            style = sideSubtitleStyle,
                            color = sideSubtitleColor
                        )
                    }
                }
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

@Preview
@Composable
private fun SemiCircularProgressBarLabelsBottomPreview() {
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
                labelPosition = LabelPosition.Bottom,
                labelPadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun SemiCircularProgressBarGradientPreview() {
    Theme {
        Box(modifier = Modifier.background(Theme.color.iconDisabled).padding(32.dp)) {
            SemiCircularProgressBar(
                progress = 0.75f,
                progressBrush = Brush.horizontalGradient(
                    colors = listOf(Theme.color.primary, Theme.color.secondary)
                ),
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

