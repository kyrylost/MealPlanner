package dev.stukalo.mealplanner.presentation.core.ui.widget.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme

@Composable
fun SelectionGroup(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    activeColor: Color = Theme.color.orange,
    inactiveColor: Color = Theme.color.gray,
    backgroundColor: Color = Color.Transparent,
    cornerRadiusDp: Dp = 16.dp,
    error: String? = null,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .clip(RoundedCornerShape(cornerRadiusDp))
                .background(backgroundColor)
                .border(
                    width = Theme.thickness.thickness1,
                    color = if (error != null) Theme.color.error else inactiveColor,
                    shape = RoundedCornerShape(cornerRadiusDp)
                )
        ) {
            options.forEachIndexed { index, option ->
                val isSelected = option == selectedOption
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(if (isSelected) activeColor else Color.Transparent)
                        .clickable { onOptionSelected(option) }
                        .padding(vertical = 12.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        style = Theme.typography.bodyLarge,
                        color = if (isSelected) Theme.color.textLight else Theme.color.text,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }

                if (index < options.size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(Theme.thickness.thickness1)
                            .background(if (error != null) Theme.color.error else inactiveColor)
                    )
                }
            }
        }

        if (error != null) {
            Text(
                text = error,
                color = Theme.color.error,
                style = Theme.typography.bodyNormal,
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
        }
    }
}
