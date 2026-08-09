package dev.stukalo.mealplanner.presentation.core.styling.color.palette.green

import dev.stukalo.mealplanner.presentation.core.styling.color.model.AppColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.BackgroundColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.BrandColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.IconColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.StateColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.SurfaceColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.TextColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.createQualityColors
import dev.stukalo.mealplanner.presentation.core.styling.color.token.ColorTokens

/**
 * A green-themed dark color palette.
 */
internal val ColorGreenDark = AppColors(
    brand = BrandColors(
        primary = ColorTokens.Green400,
        primaryVariant = ColorTokens.Green500,
        secondary = ColorTokens.Green700
    ),
    text = TextColors(
        primary = ColorTokens.OffWhite,
        secondary = ColorTokens.OffWhiteAlpha70,
        onPrimary = ColorTokens.Graphite900,
        onPrimaryVariant = ColorTokens.Graphite900Alpha70
    ),
    icon = IconColors(
        primary = ColorTokens.OffWhite,
        onPrimary = ColorTokens.Graphite900,
        disabled = ColorTokens.Graphite900
    ),
    background = BackgroundColors(
        primary = ColorTokens.Graphite900,
        secondary = ColorTokens.Gray800
    ),
    surface = SurfaceColors(
        variant = ColorTokens.Gray100
    ),
    state = StateColors(
        fixedLight = ColorTokens.OffWhite,
        fixedDark = ColorTokens.Graphite900,
        error = ColorTokens.Orange500,
        warning = ColorTokens.Yellow500,
        success = ColorTokens.GreenLight500
    ),
    quality = createQualityColors()
)
