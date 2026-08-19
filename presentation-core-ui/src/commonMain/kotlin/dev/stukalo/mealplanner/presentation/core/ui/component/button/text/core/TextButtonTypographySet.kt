package dev.stukalo.mealplanner.presentation.core.ui.component.button.text.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.button.core.ButtonTypographySet

class TextButtonTypographySet : ButtonTypographySet {
    @Composable
    override fun small(): TextStyle = Theme.typography.regular12

    @Composable
    override fun default(): TextStyle = Theme.typography.regular14
}
