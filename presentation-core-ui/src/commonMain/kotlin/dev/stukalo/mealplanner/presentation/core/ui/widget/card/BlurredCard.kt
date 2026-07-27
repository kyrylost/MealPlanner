package dev.stukalo.mealplanner.presentation.core.ui.widget.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.hazeChild

@Composable
fun BlurredCard(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    shape: Shape = Theme.shape.normalRoundedCornerShape,
    tint: Color = Theme.color.backgroundSecondary.copy(alpha = 0.7f),
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.clip(shape)) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .hazeChild(state = hazeState, tint = tint)
                .blur(12.dp)
        )
        Box {
            content()
        }
    }
}
