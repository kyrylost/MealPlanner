package dev.stukalo.mealplanner.presentation.core.styling.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * A data class representing the color palette of the application.
 *
 * This class follows the design system tokens for consistent color usage across the app.
 *
 * @property primary The main brand color.
 * @property primaryVariant A variant of the main brand color, typically darker or more saturated.
 * @property secondary An accent color for secondary UI elements.
 * @property background The primary background color for screens.
 * @property backgroundSecondary A secondary background color for grouping or layering.
 * @property surfaceVariant A color for surfaces like cards or sheets.
 * @property textPrimary The primary color for text.
 * @property textSecondary The secondary color for text, usually less prominent.
 * @property textOnPrimary The color for text displayed on top of the primary color.
 * @property textOnPrimaryVariant The color for text displayed on top of the primary variant color.
 * @property iconPrimary The primary color for icons.
 * @property iconOnPrimary The color for icons displayed on top of the primary color.
 * @property iconDisabled The color for disabled icons.
 * @property fixedLight A constant light color that doesn't change with theme.
 * @property fixedDark A constant dark color that doesn't change with theme.
 * @property error The color for error states and messages.
 * @property warning The color for warning states.
 * @property success The color for success states.
 */
@Immutable
data class AppColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val background: Color,
    val backgroundSecondary: Color,
    val surfaceVariant: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textOnPrimary: Color,
    val textOnPrimaryVariant: Color,
    val iconPrimary: Color,
    val iconOnPrimary: Color,
    val iconDisabled: Color,
    val fixedLight: Color,
    val fixedDark: Color,
    val error: Color,
    val warning: Color,
    val success: Color
)
