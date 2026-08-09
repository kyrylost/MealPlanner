package dev.stukalo.mealplanner.presentation.core.styling.color.token

import androidx.compose.ui.graphics.Color

/**
 * Design tokens for the application's color palette.
 * These are the raw color values used to build semantic color sets.
 */
internal object ColorTokens {
    // Brand Colors
    val Orange500 = Color(0xFFF17152)
    val Orange600 = Color(0xFFEB6A4B)
    val Pink500 = Color(0xFFD95C78)

    val Green400 = Color(0xFF34FFA1)
    val Green500 = Color(0xFF2DF89A)
    val Green700 = Color(0xFF00DBB1)

    // Neutral Colors
    val White = Color(0xFFFFFFFF)
    val OffWhite = Color(0xFFFCFCFC)
    val Gray100 = Color(0xFFDCDCDC)
    val Gray800 = Color(0xFF282828)
    val Graphite900 = Color(0xFF181818)

    // Translucents
    val Graphite900Alpha70 = Color(0xB2181818)
    val OffWhiteAlpha70 = Color(0xB3FCFCFC)

    // Functional Colors
    val Yellow500 = Color(0xFFF4D559)
    val GreenLight500 = Color(0xFF82E26C)

    // Quality Colors (Nutri-Score)
    val NutriScoreA = Color(0xFF00823F)
    val NutriScoreB = Color(0xFF85BB2F)
    val NutriScoreC = Color(0xFFFECB02)
    val NutriScoreD = Color(0xFFEE8100)
    val NutriScoreE = Color(0xFFE63E11)

    // Quality Colors (NOVA)
    val NovaGroup1 = Color(0xFF00AA00)
    val NovaGroup2 = Color(0xFFFFCC00)
    val NovaGroup3 = Color(0xFFFF6600)
    val NovaGroup4 = Color(0xFFFF0000)
}
