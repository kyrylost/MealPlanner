package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class Color(
    val orange: Color,
    val orangeDark: Color,
    val green: Color,
    val greenDark: Color,
    val purple: Color,
    val purpleDark: Color,
    val yellow: Color,
    val yellowDark: Color,
    val lightGray: Color,
    val gray: Color,
    val darkGray: Color,
    val background: Color,
    val backgroundSecondary: Color,
    val iconWhite: Color,
    val icon: Color,
    val textLight: Color,
    val text: Color,
)

val LocalColor = staticCompositionLocalOf {
    Color(
        orange = Color.Unspecified,
        orangeDark = Color.Unspecified,
        green = Color.Unspecified,
        greenDark = Color.Unspecified,
        purple = Color.Unspecified,
        purpleDark = Color.Unspecified,
        yellow = Color.Unspecified,
        yellowDark = Color.Unspecified,
        lightGray = Color.Unspecified,
        gray = Color.Unspecified,
        darkGray = Color.Unspecified,
        background = Color.Unspecified,
        backgroundSecondary = Color.Unspecified,
        iconWhite = Color.Unspecified,
        icon = Color.Unspecified,
        textLight = Color.Unspecified,
        text = Color.Unspecified,
    )
}

internal val ColorLight = Color(
    orange = Color(0xfff17152),
    orangeDark = Color(0xffeb6a4b),
    green = Color(0xffcde26c),
    greenDark = Color(0xffb6c858),
    purple = Color(0xffcbb1f2),
    purpleDark = Color(0xffb696e8),
    yellow = Color(0xfff4d559),
    yellowDark = Color(0xffeac431),
    lightGray = Color(0xffdcdcdc),
    gray = Color(0xff282828),
    darkGray = Color(0xff181818),
    background = Color(0xfffdfdfd),
    backgroundSecondary = Color(0xffeef1fb),
    iconWhite = Color.White,
    icon = Color(0xff181818),
    textLight = Color(0xfffcfcfc),
    text = Color(0xff181818)
)

internal val ColorDark = Color(
    orange = Color(0xfff17152),
    orangeDark = Color(0xffeb6a4b),
    green = Color(0xffcde26c),
    greenDark = Color(0xffb6c858),
    purple = Color(0xffcbb1f2),
    purpleDark = Color(0xffb696e8),
    yellow = Color(0xfff4d559),
    yellowDark = Color(0xffeac431),
    lightGray = Color(0xffdcdcdc),
    gray = Color(0xff282828),
    darkGray = Color(0xff181818),
    background = Color(0xff282828),
    backgroundSecondary = Color(0xff181818),
    iconWhite = Color.White,
    icon = Color.White,
    textLight = Color(0xfffcfcfc),
    text = Color(0xfffcfcfc)
)

