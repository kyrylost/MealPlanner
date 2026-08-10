package dev.stukalo.mealplanner.presentation.feature.main.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.core.AnimationConfiguration
import dev.stukalo.mealplanner.presentation.core.ui.haze.HazeState
import dev.stukalo.mealplanner.presentation.core.ui.haze.hazeChild
import dev.stukalo.mealplanner.presentation.core.ui.haze.rememberHazeState
import dev.stukalo.mealplanner.presentation.feature.main.navigation.inner.MainTab

/**
 * A custom bottom navigation bar that features a smooth, circular indicator transition.
 *
 * This component uses a "cutout" effect for the icons:
 * 1. The bottom [NavigationTabRow] renders all icons in the default [Theme.color.icon.primary] color.
 * 2. The top [NavigationTabRow] renders all icons in the [Theme.color.text.onPrimary] color.
 * 3. The top layer is clipped to a circular [Path] that follows the [indicatorOffset].
 *
 * This dual-layer approach allows icons to partially change color as the indicator moves
 * over them, creating a high-quality, seamless visual transition where the active color
 * is only visible "through" the moving indicator.
 *
 * @param selectedTab The currently selected [MainTab].
 * @param onTabSelected Callback invoked when a tab is clicked.
 * @param hazeState Optional [HazeState] for the blurred background effect.
 * @param modifier The modifier to be applied to the navigation bar.
 */
@Composable
fun MealPlannerBottomNavigationBar(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    hazeState: HazeState? = null,
    modifier: Modifier = Modifier
) {
    var containerWidth by remember { mutableStateOf(0) }
    val itemWidth = if (containerWidth > 0) containerWidth / MainTab.entries.size else 0
    val iconSize = 24.dp
    val indicatorSize = iconSize * 2
    val verticalPadding = Theme.spacing.space8

    val indicatorOffset by animateFloatAsState(
        targetValue = (selectedTab.ordinal * itemWidth).toFloat(),
        animationSpec = tween(durationMillis = AnimationConfiguration.Duration.NORMAL),
        label = "indicatorOffset"
    )

    Box(
        modifier =
        modifier
            .padding(Theme.spacing.space16)
            .navigationBarsPadding()
            .height(indicatorSize + verticalPadding * 2)
            .fillMaxWidth()
            .clip(CircleShape)
    ) {
        // Blurred background layer
        Box(
            modifier =
            Modifier
                .matchParentSize()
                .blur(12.dp)
                .hazeChild(
                    state = hazeState ?: HazeState(),
                    tint = Theme.color.background.secondary.copy(alpha = 0.7f)
                )
        )

        // Navigation Content layer
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .onGloballyPositioned { containerWidth = it.size.width }
        ) {
            NavigationTabRow(
                iconSize = iconSize,
                onTabSelected = onTabSelected,
                tint = Theme.color.icon.primary
            )

            if (itemWidth > 0) {
                val primaryColor = Theme.color.brand.primary
                val iconOnPrimaryColor = Theme.color.icon.onPrimary

                Box(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .drawWithContent {
                            val indicatorSizePx = indicatorSize.toPx()
                            val centerX = indicatorOffset + itemWidth / 2f
                            val centerY = size.height / 2f

                            clipPath(
                                Path().apply {
                                    addOval(
                                        Rect(
                                            left = centerX - indicatorSizePx / 2f,
                                            top = centerY - indicatorSizePx / 2f,
                                            right = centerX + indicatorSizePx / 2f,
                                            bottom = centerY + indicatorSizePx / 2f
                                        )
                                    )
                                }
                            ) {
                                drawCircle(
                                    color = primaryColor,
                                    radius = indicatorSizePx / 2f,
                                    center = Offset(centerX, centerY)
                                )
                                this@drawWithContent.drawContent()
                            }
                        }
                ) {
                    NavigationTabRow(
                        iconSize = iconSize,
                        onTabSelected = null,
                        tint = iconOnPrimaryColor
                    )
                }
            }
        }
    }
}

@Composable
private fun NavigationTabRow(
    iconSize: Dp,
    onTabSelected: ((MainTab) -> Unit)?,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MainTab.entries.forEach { tab ->
            Box(
                modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .then(
                        if (onTabSelected != null) {
                            Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onTabSelected(tab) }
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = null,
                    modifier = Modifier.size(iconSize),
                    tint = tint
                )
            }
        }
    }
}

@Composable
@Preview
fun MealPlannerBottomNavigationBarPreview() {
    var selectedTab by remember { mutableStateOf(MainTab.Home) }
    val hazeState = rememberHazeState()
    Theme {
        Box(
            modifier =
            Modifier
                .fillMaxSize()
                .background(Theme.color.background.primary)
        ) {
            MealPlannerBottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                hazeState = hazeState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}
