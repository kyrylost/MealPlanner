package dev.stukalo.mealplanner.presentation.core.styling.color.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import dev.stukalo.mealplanner.presentation.core.styling.color.token.ColorTokens

/**
 * A data class representing the food quality colors (Nutri-Score, NOVA) of the application.
 *
 * These colors remain constant (static).
 */
@Immutable
data class QualityColors(
    val nutriScoreA: Color,
    val nutriScoreB: Color,
    val nutriScoreC: Color,
    val nutriScoreD: Color,
    val nutriScoreE: Color,
    val novaGroup1: Color,
    val novaGroup2: Color,
    val novaGroup3: Color,
    val novaGroup4: Color
)

internal fun createQualityColors() = QualityColors(
    nutriScoreA = ColorTokens.NutriScoreA,
    nutriScoreB = ColorTokens.NutriScoreB,
    nutriScoreC = ColorTokens.NutriScoreC,
    nutriScoreD = ColorTokens.NutriScoreD,
    nutriScoreE = ColorTokens.NutriScoreE,
    novaGroup1 = ColorTokens.NovaGroup1,
    novaGroup2 = ColorTokens.NovaGroup2,
    novaGroup3 = ColorTokens.NovaGroup3,
    novaGroup4 = ColorTokens.NovaGroup4
)
