package dev.stukalo.mealplanner.presentation.core.ui.component.button.icon.core

import androidx.compose.animation.core.LinearEasing
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.ui.component.button.core.ButtonAnimation
import dev.stukalo.mealplanner.presentation.core.ui.component.button.core.ButtonColorSet
import dev.stukalo.mealplanner.presentation.core.ui.component.button.core.IconButtonDefault
import dev.stukalo.mealplanner.presentation.core.ui.core.AnimationConfiguration

object IconButtonDefault : IconButtonDefault {
    @Composable
    override fun buttonColor(buttonColorSet: ButtonColorSet) = IconButtonColor(buttonColorSet)

    @Composable
    override fun buttonSizeSet() = IconButtonSizeSet()

    @Composable
    override fun animation(): ButtonAnimation = object : ButtonAnimation {
        override val duration = AnimationConfiguration.Duration.NORMAL
        override val easing = LinearEasing
    }

    @Composable
    override fun corner(): Dp = 0.dp
}
