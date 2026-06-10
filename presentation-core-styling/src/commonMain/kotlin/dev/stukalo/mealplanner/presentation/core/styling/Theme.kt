package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val color: Color = when {
        darkTheme -> ColorDark
        else -> ColorLight
    }

    val typography = Typography(
        titleLarge = TextStyle(
            fontFamily = FredokaFont(),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            color = color.text,
        ),
        titleNormal = TextStyle(
            fontFamily = FredokaFont(),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            letterSpacing = 0.5.sp,
            color = color.text,
        ),
        titleSmall = TextStyle(
            fontFamily = FredokaFont(),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp,
            color = color.text,
        ),
        bodyLarge = TextStyle(
            fontFamily = FredokaFont(),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 21.sp,
            letterSpacing = 0.5.sp,
            color = color.text,
        ),
        bodyNormal = TextStyle(
            fontFamily = FredokaFont(),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp,
            color = color.text,
        ),
    )

    val elevation = Elevation(
        small = 4.dp,
        normal = 8.dp,
        large = 12.dp,
        extraLarge = 16.dp,
    )

    val radius = Radius(
        radius2 = 2.dp,
        radius4 = 4.dp,
        radius8 = 8.dp,
        radius12 = 12.dp,
        radius16 = 16.dp,
        radius20 = 20.dp,
        radius24 = 24.dp,
        radius28 = 28.dp,
        radius32 = 32.dp,
        radius48 = 48.dp,
        radius64 = 64.dp,
    )

    val spacing = Spacing(
        space2 = 2.dp,
        space4 = 4.dp,
        space8 = 8.dp,
        space12 = 12.dp,
        space16 = 16.dp,
        space20 = 20.dp,
        space24 = 24.dp,
        space28 = 28.dp,
        space32 = 32.dp,
        space48 = 48.dp,
        space64 = 64.dp,
    )

    val thickness = Thickness(
        thickness1 = 1.dp,
        thickness2 = 2.dp,
        thickness4 = 4.dp,
    )

    val shape = Shape(
        largeRoundedCornerShape = RoundedCornerShape(radius.radius24),
        normalRoundedCornerShape = RoundedCornerShape(radius.radius16),
    )

    CompositionLocalProvider(
        LocalColor provides color,
        LocalTypography provides typography,
        LocalElevation provides elevation,
        LocalShape provides shape,
        LocalSpacing provides spacing,
        LocalRadius provides radius,
        LocalThickness provides thickness,
        content = content
    )
}

object Theme {
    val color: Color
        @Composable
        get() = LocalColor.current
    val typography: Typography
        @Composable
        get() = LocalTypography.current
    val elevation: Elevation
        @Composable
        get() = LocalElevation.current
    val shape: Shape
        @Composable
        get() = LocalShape.current
    val spacing: Spacing
        @Composable
        get() = LocalSpacing.current
    val radius: Radius
        @Composable
        get() = LocalRadius.current
    val thickness: Thickness
        @Composable
        get() = LocalThickness.current
}