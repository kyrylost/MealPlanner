package dev.stukalo.mealplanner.presentation.core.styling.typography

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

val LocalTypography = staticCompositionLocalOf {
    Typography(
        bold48 = TextStyle.Default,
        bold16 = TextStyle.Default,
        bold14 = TextStyle.Default,
        bold12 = TextStyle.Default,
        semibold48 = TextStyle.Default,
        regular48 = TextStyle.Default,
        regular14 = TextStyle.Default,
        regular12 = TextStyle.Default,
    )
}
