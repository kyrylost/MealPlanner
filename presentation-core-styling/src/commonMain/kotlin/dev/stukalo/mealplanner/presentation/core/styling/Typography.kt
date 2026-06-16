package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import mealplanner.presentation_core_styling.generated.resources.Res
import mealplanner.presentation_core_styling.generated.resources.fredoka_bold
import mealplanner.presentation_core_styling.generated.resources.fredoka_light
import mealplanner.presentation_core_styling.generated.resources.fredoka_medium
import mealplanner.presentation_core_styling.generated.resources.fredoka_regular
import mealplanner.presentation_core_styling.generated.resources.fredoka_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun FredokaFont() = FontFamily(
    Font(Res.font.fredoka_light, weight = FontWeight.Light),
    Font(Res.font.fredoka_regular, weight = FontWeight.Normal),
    Font(Res.font.fredoka_medium, weight = FontWeight.Medium),
    Font(Res.font.fredoka_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.fredoka_bold, weight = FontWeight.Bold),
)

@Immutable
data class Typography(
    val bold48: TextStyle,
    val bold16: TextStyle,
    val bold14: TextStyle,
    val bold12: TextStyle,
    val semibold48: TextStyle,
    val regular48: TextStyle,
    val regular14: TextStyle,
    val regular12: TextStyle,
)

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
