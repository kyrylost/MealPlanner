package dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.core.ButtonTypographySet

class PrimaryButtonTypographySet : ButtonTypographySet {
    @Composable
    override fun small(): TextStyle = Theme.typography.bold12

    @Composable
    override fun default(): TextStyle = Theme.typography.bold14
}
