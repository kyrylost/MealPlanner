package dev.stukalo.mealplanner.presentation.core.ui.widget.button.text.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.ButtonTypographySet
import dev.stukalo.mealplanner.presentation.core.styling.Theme

class TextButtonTypographySet : ButtonTypographySet {
    @Composable
    override fun small(): TextStyle = Theme.typography.regular12

    @Composable
    override fun default(): TextStyle = Theme.typography.regular14
}
