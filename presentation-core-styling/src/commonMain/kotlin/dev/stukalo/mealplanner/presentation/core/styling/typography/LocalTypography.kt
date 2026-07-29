package dev.stukalo.mealplanner.presentation.core.styling.typography

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

val LocalTypography = staticCompositionLocalOf {
    Typography(
        bold48 = TextStyle.Default,
        bold36 = TextStyle.Default,
        bold16 = TextStyle.Default,
        bold14 = TextStyle.Default,
        bold12 = TextStyle.Default,
        semibold48 = TextStyle.Default,
        semibold36 = TextStyle.Default,
        semibold16 = TextStyle.Default,
        regular48 = TextStyle.Default,
        regular14 = TextStyle.Default,
        regular12 = TextStyle.Default,
        handwrittenRegular64 = TextStyle.Default,
        handwrittenSemibold64 = TextStyle.Default,
    )
}
