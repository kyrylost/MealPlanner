package dev.stukalo.mealplanner.presentation.core.styling.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import mealplanner.presentation_core_styling.generated.resources.Res
import mealplanner.presentation_core_styling.generated.resources.caveat_bold
import mealplanner.presentation_core_styling.generated.resources.caveat_medium
import mealplanner.presentation_core_styling.generated.resources.caveat_regular
import mealplanner.presentation_core_styling.generated.resources.caveat_semibold
import mealplanner.presentation_core_styling.generated.resources.nunito_bold
import mealplanner.presentation_core_styling.generated.resources.nunito_light
import mealplanner.presentation_core_styling.generated.resources.nunito_medium
import mealplanner.presentation_core_styling.generated.resources.nunito_regular
import mealplanner.presentation_core_styling.generated.resources.nunito_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun nunitoFont() = FontFamily(
    Font(Res.font.nunito_light, weight = FontWeight.Light),
    Font(Res.font.nunito_regular, weight = FontWeight.Normal),
    Font(Res.font.nunito_medium, weight = FontWeight.Medium),
    Font(Res.font.nunito_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.nunito_bold, weight = FontWeight.Bold),
)

@Composable
fun caveatFont() = FontFamily(
    Font(Res.font.caveat_regular, weight = FontWeight.Normal),
    Font(Res.font.caveat_medium, weight = FontWeight.Medium),
    Font(Res.font.caveat_semibold, weight = FontWeight.SemiBold),
    Font(Res.font.caveat_bold, weight = FontWeight.Bold),
)
