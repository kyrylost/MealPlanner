package dev.stukalo.mealplanner.presentation.core.styling.color.palette.pink

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
 * A pink-themed light color palette.
 */
internal val ColorPinkLight = AppColors(
    brand = BrandColors(
        primary = ColorTokens.Pink500,
        primaryVariant = ColorTokens.Pink600,
        secondary = ColorTokens.Pink900
    ),
    text = TextColors(
        primary = ColorTokens.Graphite900,
        secondary = ColorTokens.Graphite900Alpha70,
        onPrimary = ColorTokens.OffWhite,
        onPrimaryVariant = ColorTokens.OffWhiteAlpha70
    ),
    icon = IconColors(
        primary = ColorTokens.Gray800,
        onPrimary = ColorTokens.OffWhite,
        disabled = ColorTokens.Graphite900
    ),
    background = BackgroundColors(
        primary = ColorTokens.White,
        secondary = ColorTokens.OffWhite
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
