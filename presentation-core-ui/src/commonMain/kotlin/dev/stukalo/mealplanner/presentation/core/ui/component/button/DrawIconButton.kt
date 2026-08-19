package dev.stukalo.mealplanner.presentation.core.ui.component.button

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.core.AnimationConfiguration
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconPreview16
import io.github.alexzhirkevich.compottie.Compottie.IterateForever
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import mealplanner.presentation_core_ui.generated.resources.Res

/**
 * A composable function that represents a icon button with optional loading animation.
 *
 * This function creates a button with customizable properties for appearance, such as background
 * color, border color, shape and icon size.
 * It also supports a loading state that displays a Lottie animation.
 *
 * @param modifier The modifier to be applied to the button.
 * @param icon The icon to display on the button.
 * @param backgroundColor The background color of the button.
 * @param iconColor The icon color of the button.
 * @param borderColor The border color of the button.
 * @param corner The corners of the button.
 * @param iconSize The size of the icons within the button.
 * @param loadingSize The size of the loading indicator within the button.
 * @param borderSize The size of the border around the button.
 * @param minHeight The minimum height of the button.
 * @param paddings The padding values for the content within the button.
 * @param isLoading A boolean indicating whether the button is in a loading state.
 * @param horizontalArrangement The horizontal arrangement of the layout's children.
 *
 * References:
 *
 * - https://proandroiddev.com/compose-a-compose-button-by-composing-composable-functions-9f275772bd23
 * - https://github.com/aoriani/ComposeButton/tree/main
 */
@Composable
fun DrawIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    backgroundColor: Color,
    iconColor: Color,
    borderColor: Color,
    corner: Dp,
    iconSize: Dp,
    loadingSize: Dp,
    borderSize: Dp,
    minHeight: Dp,
    paddings: PaddingValues,
    isLoading: Boolean,
    horizontalArrangement: Alignment = Alignment.Center
) {
    //region core
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/animation.json").decodeToString()
        )
    }

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = IterateForever
    )
    //endregion core

    Box(
        modifier =
        modifier
            .animateContentSize(
                animationSpec =
                tween(
                    durationMillis = AnimationConfiguration.Duration.NORMAL,
                    easing = LinearOutSlowInEasing
                )
            ).border(
                width = borderSize,
                color = borderColor,
                shape = RoundedCornerShape(corner)
            ).background(
                color = backgroundColor,
                shape = RoundedCornerShape(corner)
            ).padding(paddings),
        contentAlignment = horizontalArrangement
    ) {
        AnimatedContent(
            targetState = isLoading,
            transitionSpec = AnimationConfiguration.Transition.default(),
            label = "DrawButton: AnimatedContent"
        ) {
            Box(
                modifier =
                Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                when {
                    it ->
                        Image(
                            modifier = Modifier.size(loadingSize),
                            painter =
                            rememberLottiePainter(
                                composition = composition,
                                progress = { progress },
                                enableMergePaths = true
                            ),
                            contentDescription = "Lottie animation"
                        )

                    !it ->
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(iconSize)
                        )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDrawIconButton() {
    Theme {
        Box(Modifier.padding(16.dp)) {
            DrawIconButton(
                modifier = Modifier.wrapContentWidth(),
                icon = IconPreview16,
                backgroundColor = Color.White,
                iconColor = Color.Black,
                borderColor = Color.Black,
                corner = 0.dp,
                iconSize = 24.dp,
                borderSize = 1.dp,
                minHeight = 64.dp,
                paddings = PaddingValues(all = 8.dp),
                isLoading = false,
                loadingSize = 0.dp,
                horizontalArrangement = Alignment.TopStart
            )
        }
    }
}
