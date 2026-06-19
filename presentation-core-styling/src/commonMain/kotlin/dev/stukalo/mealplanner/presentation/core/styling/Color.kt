package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class Color(
    val primary: Color,
    val primaryVariant: Color,
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
    val error: Color,
    val warning: Color,
    val success: Color,
)

val LocalColor = staticCompositionLocalOf {
    Color(
        primary = Color.Unspecified,
        primaryVariant = Color.Unspecified,
        background = Color.Unspecified,
        backgroundSecondary = Color.Unspecified,
        surfaceVariant = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        textOnPrimary = Color.Unspecified,
        textOnPrimaryVariant = Color.Unspecified,
        iconPrimary = Color.Unspecified,
        iconOnPrimary = Color.Unspecified,
        iconDisabled = Color.Unspecified,
        error = Color.Unspecified,
        warning = Color.Unspecified,
        success = Color.Unspecified,
    )
}

internal val ColorLight = Color(
    primary = Color(0xfff17152),
    primaryVariant = Color(0xffeb6a4b),
    background = Color(0xfffdfdfd),
    backgroundSecondary = Color(0xffeef1fb),
    surfaceVariant = Color(0xffdcdcdc),
    textPrimary = Color(0xff181818),
    textSecondary = Color(0xB2181818),
    textOnPrimary = Color(0xfffcfcfc),
    textOnPrimaryVariant = Color(0xB3FCFCFC),
    iconPrimary = Color(0xff282828),
    iconOnPrimary = Color(0xfffcfcfc),
    iconDisabled = Color(0xff181818),
    error = Color(0xfff17152),
    warning = Color(0xfff4d559),
    success = Color(0xFF82E26C),
)

internal val ColorDark = Color(
    primary = Color(0xfff17152),
    primaryVariant = Color(0xffeb6a4b),
    background = Color(0xff181818),
    backgroundSecondary = Color(0xff282828),
    surfaceVariant = Color(0xffdcdcdc),
    textPrimary = Color(0xfffcfcfc),
    textSecondary = Color(0xB2FCFCFC),
    textOnPrimary = Color(0xfffcfcfc),
    textOnPrimaryVariant = Color(0xB3FCFCFC),
    iconPrimary = Color(0xfffcfcfc),
    iconOnPrimary = Color(0xfffcfcfc),
    iconDisabled = Color(0xff181818),
    error = Color(0xfff17152),
    warning = Color(0xfff4d559),
    success = Color(0xFF82E26C),
)
