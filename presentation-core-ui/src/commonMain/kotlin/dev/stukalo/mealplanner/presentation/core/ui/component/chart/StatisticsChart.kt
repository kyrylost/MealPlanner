package dev.stukalo.mealplanner.presentation.core.ui.component.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_format_percentage
import dev.stukalo.mealplanner.core.localization.statistics_no_data
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import org.jetbrains.compose.resources.stringResource

/**
 * A custom-drawn chart for displaying statistics.
 * Supports [ChartStyle.BAR] for discrete data and [ChartStyle.LINE] for trend data.
 *
 * @param points List of [ChartPoint] to display.
 * @param style Visual style of the chart (BAR or LINE).
 * @param targetValue Optional global target value to draw a goal line (primarily for LINE style).
 * @param modifier Modifier for the chart container.
 */
@Composable
fun StatisticsChart(
    points: List<ChartPoint>,
    style: ChartStyle = ChartStyle.BAR,
    targetValue: Double? = null,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val labelStyle = Theme.typography.regular12.copy(
        color = Theme.color.text.secondary,
        textAlign = TextAlign.Center
    )

    val percentageFormat = stringResource(Res.string.common_format_percentage)

    val xAxisLabelOffset = Theme.spacing.space12
    val pointRadius = Theme.radius.radius4
    val gridThickness = Theme.thickness.thickness1
    val lineThickness = Theme.thickness.thickness2

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(CHART_HEIGHT_DP)
            .padding(vertical = Theme.spacing.space16),
        contentAlignment = Alignment.Center
    ) {
        if (points.isEmpty()) {
            Text(
                text = stringResource(Res.string.statistics_no_data),
                style = Theme.typography.regular14,
                color = Theme.color.text.secondary
            )
        }

        val primaryColor = Theme.color.brand.primary
        val secondaryColor = Theme.color.text.secondary.copy(alpha = 0.2f)
        val radius = Theme.radius.radius8

        Canvas(modifier = Modifier.fillMaxSize()) {
            if (points.isEmpty()) return@Canvas

            val yAxisWidth = Y_AXIS_WIDTH_DP.toPx()
            val xAxisHeight = X_AXIS_HEIGHT_DP.toPx()
            val chartWidth = size.width - yAxisWidth
            val chartHeight = size.height - xAxisHeight

            val maxDataValue = points.maxOf { it.value }.coerceAtLeast(1.0)
            val minDataValue = if (style == ChartStyle.LINE) {
                points.filter { it.value > 0 }.minOfOrNull { it.value } ?: 0.0
            } else {
                0.0
            }

            val targetValueForLine = if (style == ChartStyle.LINE) targetValue else null
            val targetValueForBar = if (style == ChartStyle.BAR) points.firstOrNull()?.target else null

            // Adjust max value to include target and provide padding
            val rangePadding = if (style == ChartStyle.LINE) {
                (maxDataValue - minDataValue).coerceAtLeast(maxDataValue * 0.1) * LINE_CHART_PADDING_FACTOR
            } else {
                0.0
            }

            val effectiveMax = when (style) {
                ChartStyle.BAR -> maxOf(maxDataValue, (targetValueForBar ?: 0.0) * BAR_CHART_TARGET_MULTIPLIER)
                ChartStyle.LINE -> if (targetValueForLine != null) {
                    maxOf(maxDataValue, targetValueForLine) + rangePadding
                } else {
                    maxDataValue + rangePadding
                }
            }

            val effectiveMin = if (style == ChartStyle.LINE) {
                if (targetValueForLine != null) {
                    minOf(minDataValue, targetValueForLine) - rangePadding
                } else {
                    minOf(minDataValue, maxDataValue) - rangePadding
                }
            } else {
                0.0
            }

            val valueRange = (effectiveMax - effectiveMin).coerceAtLeast(1.0)
            val itemWidth = chartWidth / points.size
            val radiusPx = radius.toPx()

            // Draw Y-Axis Labels and Grid Lines
            val labelCount = 4
            for (i in 0 until labelCount) {
                val ratio = i / (labelCount - 1).toFloat()
                val y = chartHeight - ratio * chartHeight
                val value = effectiveMin + ratio * (effectiveMax - effectiveMin)

                val label = if (style == ChartStyle.BAR && targetValueForBar != null && targetValueForBar > 0) {
                    percentageFormat.replace("%1\$d", (value / targetValueForBar * 100).toInt().toString())
                } else {
                    value.toInt().toString()
                }

                val textLayoutResult = textMeasurer.measure(label, labelStyle)
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        (yAxisWidth - textLayoutResult.size.width) / 2,
                        y - textLayoutResult.size.height / 2
                    )
                )

                drawLine(
                    color = secondaryColor,
                    start = Offset(yAxisWidth, y),
                    end = Offset(size.width, y),
                    strokeWidth = gridThickness.toPx()
                )
            }

            // Draw Target Line
            if (targetValueForLine != null || targetValueForBar != null) {
                val target = targetValueForLine ?: targetValueForBar ?: 0.0
                val targetY = chartHeight - ((target - effectiveMin) / valueRange * chartHeight).toFloat()

                drawLine(
                    color = primaryColor.copy(alpha = 0.5f),
                    start = Offset(yAxisWidth, targetY),
                    end = Offset(size.width, targetY),
                    strokeWidth = lineThickness.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(DASH_ON_INTERVAL, DASH_OFF_INTERVAL),
                        0f
                    )
                )
            }

            when (style) {
                ChartStyle.BAR -> {
                    val barWidth = itemWidth * BAR_WIDTH_FACTOR
                    val spacing = itemWidth * BAR_SPACING_FACTOR
                    points.forEachIndexed { index, point ->
                        val x = yAxisWidth + index * itemWidth + spacing / 2
                        val barHeight = (point.value / effectiveMax) * chartHeight

                        // Background bar up to 100%
                        val targetHeight = (point.target ?: 0.0) / effectiveMax * chartHeight
                        drawRoundRect(
                            color = secondaryColor,
                            topLeft = Offset(x, (chartHeight - targetHeight).toFloat()),
                            size = Size(barWidth, targetHeight.toFloat()),
                            cornerRadius = CornerRadius(radiusPx, radiusPx)
                        )

                        // Actual value bar
                        drawRoundRect(
                            color = primaryColor,
                            topLeft = Offset(x, (chartHeight - barHeight).toFloat()),
                            size = Size(barWidth, barHeight.toFloat()),
                            cornerRadius = CornerRadius(radiusPx, radiusPx)
                        )

                        // Label
                        if (point.label.isNotEmpty()) {
                            val textLayoutResult = textMeasurer.measure(point.label, labelStyle)
                            drawText(
                                textLayoutResult = textLayoutResult,
                                topLeft = Offset(
                                    x + (barWidth - textLayoutResult.size.width) / 2,
                                    chartHeight + xAxisLabelOffset.toPx()
                                )
                            )
                        }
                    }
                }
                ChartStyle.LINE -> {
                    val path = androidx.compose.ui.graphics.Path()
                    var isFirstPoint = true

                    points.forEachIndexed { index, point ->
                        val x = yAxisWidth + index * itemWidth + itemWidth / 2

                        // Draw Label
                        if (point.label.isNotEmpty()) {
                            val textLayoutResult = textMeasurer.measure(point.label, labelStyle)
                            drawText(
                                textLayoutResult = textLayoutResult,
                                topLeft = Offset(
                                    x - textLayoutResult.size.width / 2,
                                    chartHeight + xAxisLabelOffset.toPx()
                                )
                            )
                        }

                        // Draw Point and Path (Only if value exists)
                        if (point.value > 0) {
                            val y = (chartHeight - ((point.value - effectiveMin) / valueRange) * chartHeight).toFloat()

                            if (isFirstPoint) {
                                path.moveTo(x, y)
                                isFirstPoint = false
                            } else {
                                path.lineTo(x, y)
                            }

                            drawCircle(
                                color = primaryColor,
                                radius = pointRadius.toPx(),
                                center = Offset(x, y)
                            )
                        }
                    }
                    drawPath(
                        path = path,
                        color = primaryColor,
                        style = Stroke(width = lineThickness.toPx())
                    )
                }
            }
        }
    }
}

private val CHART_HEIGHT_DP = 260.dp
private val Y_AXIS_WIDTH_DP = 40.dp
private val X_AXIS_HEIGHT_DP = 30.dp

private const val BAR_CHART_TARGET_MULTIPLIER = 1.5f
private const val LINE_CHART_PADDING_FACTOR = 0.1f
private const val BAR_WIDTH_FACTOR = 0.6f
private const val BAR_SPACING_FACTOR = 0.4f
private const val DASH_ON_INTERVAL = 10f
private const val DASH_OFF_INTERVAL = 10f

@Preview
@Composable
private fun StatisticsChartBarPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            StatisticsChart(
                points = listOf(
                    ChartPoint(1500.0, 2000.0, "Mon"),
                    ChartPoint(1800.0, 2000.0, "Tue"),
                    ChartPoint(2200.0, 2000.0, "Wed"),
                    ChartPoint(1200.0, 2000.0, "Thu"),
                    ChartPoint(2000.0, 2000.0, "Fri")
                ),
                style = ChartStyle.BAR,
                modifier = Modifier.padding(Theme.spacing.space16)
            )
        }
    }
}

@Preview
@Composable
private fun StatisticsChartLinePreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            StatisticsChart(
                points = listOf(
                    ChartPoint(78.5, label = "01.08"),
                    ChartPoint(78.2, label = "02.08"),
                    ChartPoint(77.9, label = "03.08"),
                    ChartPoint(78.1, label = "04.08"),
                    ChartPoint(77.5, label = "05.08")
                ),
                style = ChartStyle.LINE,
                modifier = Modifier.padding(Theme.spacing.space16)
            )
        }
    }
}

@Preview
@Composable
private fun StatisticsChartEmptyPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            StatisticsChart(
                points = emptyList(),
                modifier = Modifier.padding(Theme.spacing.space16)
            )
        }
    }
}
