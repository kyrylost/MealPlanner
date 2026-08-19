package dev.stukalo.mealplanner.presentation.core.ui.component.button.primary.core

import androidx.compose.animation.core.LinearEasing
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.button.core.ButtonAnimation
import dev.stukalo.mealplanner.presentation.core.ui.component.button.core.ButtonColorSet
import dev.stukalo.mealplanner.presentation.core.ui.component.button.core.ButtonDefault
import dev.stukalo.mealplanner.presentation.core.ui.component.button.core.ButtonTypographySet
import dev.stukalo.mealplanner.presentation.core.ui.core.AnimationConfiguration

object PrimaryButtonDefault : ButtonDefault {
    @Composable
    override fun buttonColor(buttonColorSet: ButtonColorSet) = PrimaryButtonColor(buttonColorSet)

    @Composable
    override fun buttonSizeSet() = PrimaryButtonSizeSet()

    @Composable
    override fun animation(): ButtonAnimation = object : ButtonAnimation {
        override val duration = AnimationConfiguration.Duration.NORMAL
        override val easing = LinearEasing
    }

    @Composable
    override fun corner(): Dp = Theme.radius.radius24

    @Composable
    override fun typography(): ButtonTypographySet = PrimaryButtonTypographySet()
}
