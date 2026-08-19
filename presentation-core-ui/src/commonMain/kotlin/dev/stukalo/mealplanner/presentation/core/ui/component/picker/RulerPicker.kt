package dev.stukalo.mealplanner.presentation.core.ui.component.picker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.button.icon.IconButton
import dev.stukalo.mealplanner.presentation.core.ui.component.button.icon.core.IconButtonColor
import dev.stukalo.mealplanner.presentation.core.ui.component.button.icon.core.IconButtonSizeSet
import dev.stukalo.mealplanner.presentation.core.ui.component.button.icon.core.iconButtonDefaultColorSet
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconEdit
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * A horizontal ruler-style picker component.
 *
 * The picker displays a series of ticks that can be dragged horizontally to select a value within a [range].
 * The design features a header with a label on the left and the current value on the right.
 * The ticks fade in size and color from the center towards the edges.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param label The title text displayed on the top left of the picker.
 * @param value The current value selected in the picker.
 * @param onValueChange Callback invoked when the value changes during dragging.
 * @param range The range of values the picker can select from.
 * @param step The value increment between each tick on the ruler. Decreasing this (e.g. from 1f to 0.5f)
 *             will increase the total number of lines in the range.
 * @param unit The unit string displayed next to the value (e.g., "kg", "cm").
 * @param lineSpacing The horizontal distance between each tick. Decreasing this (e.g. from 8.dp to 4.dp)
 *                  will increase the density of lines visible on the screen.
 * @param centerTickColor The color of the tick at the center of the picker.
 * @param edgeTickColor The color of the ticks as they approach the edges of the picker.
 * @param onEditClick Callback invoked when the edit icon next to the value is clicked.
 */
@Composable
fun RulerPicker(
    modifier: Modifier = Modifier,
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>,
    step: Float = 1f,
    unit: String = "",
    lineSpacing: Dp = 16.dp,
    centerTickColor: Color = Theme.color.brand.primary,
    edgeTickColor: Color = Theme.color.background.secondary,
    onEditClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val lineSpacingPx = with(density) { lineSpacing.toPx() }

    val totalSteps = ((range.endInclusive - range.start) / step).toInt()

    // Internal offset to track drag.
    var dragOffset by remember(value) {
        mutableFloatStateOf(
            -(value - range.start) / step * lineSpacingPx
        )
    }

    val textPrimaryColor = Theme.color.text.primary
    val textSecondaryColor = Theme.color.text.secondary

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Header: Label and Value
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing.space16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = Theme.typography.bold16,
                color = textPrimaryColor
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                val formattedValue = if (value % 1f ==
                    0f
                ) {
                    value.toInt().toString()
                } else {
                    value.toString()
                }
                Text(
                    text = formattedValue,
                    style = Theme.typography.bold16,
                    color = textPrimaryColor
                )
                Text(
                    text = " $unit",
                    style = Theme.typography.regular12,
                    color = textSecondaryColor
                )

                IconButton(
                    icon = IconEdit,
                    sizes = IconButtonSizeSet().small(),
                    colors =
                    IconButtonColor(
                        buttonColorSet =
                        iconButtonDefaultColorSet()
                            .copy(
                                foregroundColorDefault = Theme.color.brand.primary
                            )
                    ),
                    onClick = onEditClick
                )
            }
        }

        BoxWithConstraints(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state =
                    rememberDraggableState { delta ->
                        val newOffset = dragOffset + delta
                        val minValueOffset = -totalSteps * lineSpacingPx
                        val maxValueOffset = 0f

                        if (newOffset in minValueOffset..maxValueOffset) {
                            dragOffset = newOffset
                            val newValue = range.start + (-dragOffset / lineSpacingPx * step)
                            val snappedValue =
                                ((newValue - range.start) / step).roundToInt() * step + range.start
                            if (snappedValue != value) {
                                onValueChange(snappedValue)
                            }
                        }
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            val centerPx = constraints.maxWidth / 2f

            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val yCenter = canvasHeight / 2

                val maxTickHeight = 60.dp.toPx()
                val minTickHeight = 10.dp.toPx()

                for (i in 0..totalSteps) {
                    val x = centerPx + dragOffset + (i * lineSpacingPx)

                    if (x !in 0f..canvasWidth) continue

                    val distance = abs(x - centerPx)
                    val maxDistance = canvasWidth / 2
                    val ratio = (distance / maxDistance).coerceIn(0f, 1f)

                    val tickHeight = maxTickHeight - (maxTickHeight - minTickHeight) * ratio
                    val tickColor = lerp(centerTickColor, edgeTickColor, ratio)

                    drawLine(
                        color = tickColor,
                        start = Offset(x, yCenter - tickHeight / 2),
                        end = Offset(x, yCenter + tickHeight / 2),
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RulerPickerPreview() {
    var value by remember { mutableFloatStateOf(70f) }
    Theme {
        Surface(color = Theme.color.background.primary) {
            RulerPicker(
                label = "Weight",
                value = value,
                onValueChange = { value = it },
                range = 40f..150f,
                unit = "kg",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
