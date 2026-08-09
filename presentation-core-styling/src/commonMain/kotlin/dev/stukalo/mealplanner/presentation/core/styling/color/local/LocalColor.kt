package dev.stukalo.mealplanner.presentation.core.styling.color.local

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import dev.stukalo.mealplanner.presentation.core.styling.color.model.AppColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.BackgroundColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.BrandColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.IconColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.QualityColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.StateColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.SurfaceColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.TextColors

/**
 * CompositionLocal for [AppColors] to provide theme colors throughout the hierarchy.
 */
val LocalColor =
    staticCompositionLocalOf {
        AppColors(
            brand = BrandColors(
                primary = Color.Unspecified,
                primaryVariant = Color.Unspecified,
                secondary = Color.Unspecified
            ),
            text = TextColors(
                primary = Color.Unspecified,
                secondary = Color.Unspecified,
                onPrimary = Color.Unspecified,
                onPrimaryVariant = Color.Unspecified
            ),
            icon = IconColors(
                primary = Color.Unspecified,
                onPrimary = Color.Unspecified,
                disabled = Color.Unspecified
            ),
            background = BackgroundColors(
                primary = Color.Unspecified,
                secondary = Color.Unspecified
            ),
            surface = SurfaceColors(
                variant = Color.Unspecified
            ),
            state = StateColors(
                fixedLight = Color.Unspecified,
                fixedDark = Color.Unspecified,
                error = Color.Unspecified,
                warning = Color.Unspecified,
                success = Color.Unspecified
            ),
            quality = QualityColors(
                nutriScoreA = Color.Unspecified,
                nutriScoreB = Color.Unspecified,
                nutriScoreC = Color.Unspecified,
                nutriScoreD = Color.Unspecified,
                nutriScoreE = Color.Unspecified,
                novaGroup1 = Color.Unspecified,
                novaGroup2 = Color.Unspecified,
                novaGroup3 = Color.Unspecified,
                novaGroup4 = Color.Unspecified
            )
        )
    }
