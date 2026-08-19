package dev.stukalo.mealplanner.presentation.core.ui.component.selector

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.core.AnimationConfiguration

/**
 * A generic segmented selector component with a sliding animation.
 *
 * @param items The list of items to display in the selector.
 * @param selectedItem The currently selected item.
 * @param onItemSelected Callback when an item is clicked.
 * @param label A lambda that returns the string representation of an item.
 * @param modifier The modifier for the selector.
 */
@Composable
fun <T> SegmentedSelector(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    label: @Composable (T) -> String,
    modifier: Modifier = Modifier
) {
    val selectedIndex = items.indexOf(selectedItem).coerceAtLeast(0)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(Theme.shape.normalRoundedCornerShape)
            .background(Theme.color.background.secondary)
            .padding(Theme.spacing.space4)
    ) {
        val maxWidth = maxWidth
        val itemWidth = maxWidth / items.size

        val indicatorOffset by animateDpAsState(
            targetValue = itemWidth * selectedIndex,
            animationSpec = tween(durationMillis = AnimationConfiguration.Duration.NORMAL)
        )

        // Sliding Background Indicator
        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .width(itemWidth)
                .fillMaxHeight()
                .clip(Theme.shape.normalRoundedCornerShape)
                .background(Theme.color.brand.primary)
        )

        Row(modifier = Modifier.fillMaxSize()) {
            items.forEach { item ->
                val isSelected = item == selectedItem
                val textColor by animateColorAsState(
                    targetValue = if (isSelected) Theme.color.text.onPrimary else Theme.color.text.primary,
                    animationSpec = tween(durationMillis = AnimationConfiguration.Duration.NORMAL)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onItemSelected(item) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label(item),
                        style = Theme.typography.bold12,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SegmentedSelectorPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            SegmentedSelector(
                items = listOf("Week", "Month", "Year"),
                selectedItem = "Week",
                onItemSelected = {},
                label = { it },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
