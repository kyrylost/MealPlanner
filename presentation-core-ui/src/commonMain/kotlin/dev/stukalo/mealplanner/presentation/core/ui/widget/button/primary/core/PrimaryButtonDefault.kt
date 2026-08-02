package dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.core

import androidx.compose.animation.core.LinearEasing
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonAnimation
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonColorSet
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonDefault
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonTypographySet
import dev.stukalo.mealplanner.presentation.core.ui.core.AnimationConfiguration

object PrimaryButtonDefault : ButtonDefault {
    @Composable
    override fun buttonColor(buttonColorSet: ButtonColorSet) = PrimaryButtonColor(buttonColorSet)

    @Composable
    override fun buttonSizeSet() = PrimaryButtonSizeSet()

    @Composable
    override fun animation(): ButtonAnimation = object : ButtonAnimation {
        override val duration = AnimationConfiguration.Duration.DEFAULT
        override val easing = LinearEasing
    }

    @Composable
    override fun corner(): Dp = 0.dp

    @Composable
    override fun typography(): ButtonTypographySet = PrimaryButtonTypographySet()
}
