package dev.stukalo.mealplanner.presentation.core.ui.widget.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.hazeChild

/**
 * A card with a blurred background effect using Haze.
 *
 * @param modifier The modifier to be applied to the card.
 * @param hazeState The state of the Haze effect.
 * @param shape The shape of the card.
 * @param tint The tint color applied to the blurred background.
 * @param content The content to be displayed inside the card.
 */
@Composable
fun BlurredCard(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    shape: Shape = Theme.shape.normalRoundedCornerShape,
    tint: Color = Theme.color.background.secondary.copy(alpha = 0.7f),
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier.clip(shape)) {
        Box(
            modifier =
            Modifier
                .matchParentSize()
                .hazeChild(state = hazeState, tint = tint)
                .blur(12.dp)
        )
        Box {
            content()
        }
    }
}

@Preview
@Composable
private fun BlurredCardPreview() {
    Theme {
        BlurredCard(
            modifier = Modifier.size(200.dp),
            hazeState = HazeState()
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
            )
        }
    }
}
