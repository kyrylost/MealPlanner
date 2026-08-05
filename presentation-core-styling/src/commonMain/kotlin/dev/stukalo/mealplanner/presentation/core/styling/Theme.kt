package dev.stukalo.mealplanner.presentation.core.styling

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.stukalo.mealplanner.presentation.core.styling.color.AppColors
import dev.stukalo.mealplanner.presentation.core.styling.color.ColorDark
import dev.stukalo.mealplanner.presentation.core.styling.color.ColorGreenDark
import dev.stukalo.mealplanner.presentation.core.styling.color.ColorLight
import dev.stukalo.mealplanner.presentation.core.styling.color.LocalColor
import dev.stukalo.mealplanner.presentation.core.styling.color.ThemeColorPalette
import dev.stukalo.mealplanner.presentation.core.styling.color.animateColors
import dev.stukalo.mealplanner.presentation.core.styling.dimension.AspectRatio
import dev.stukalo.mealplanner.presentation.core.styling.dimension.Elevation
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalAspectRatio
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalElevation
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalRadius
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalSize
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalSpacing
import dev.stukalo.mealplanner.presentation.core.styling.dimension.LocalThickness
import dev.stukalo.mealplanner.presentation.core.styling.dimension.Radius
import dev.stukalo.mealplanner.presentation.core.styling.dimension.Size
import dev.stukalo.mealplanner.presentation.core.styling.dimension.Spacing
import dev.stukalo.mealplanner.presentation.core.styling.dimension.Thickness
import dev.stukalo.mealplanner.presentation.core.styling.shape.LocalShape
import dev.stukalo.mealplanner.presentation.core.styling.shape.Shape
import dev.stukalo.mealplanner.presentation.core.styling.typography.LocalTypography
import dev.stukalo.mealplanner.presentation.core.styling.typography.Typography
import dev.stukalo.mealplanner.presentation.core.styling.typography.caveatFont
import dev.stukalo.mealplanner.presentation.core.styling.typography.nunitoFont

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    palette: ThemeColorPalette = ThemeColorPalette.ORANGE,
    animatePaletteChange: Boolean = true,
    content: @Composable () -> Unit
) {
    val targetColors: AppColors =
        when (palette) {
            ThemeColorPalette.ORANGE -> if (darkTheme) ColorDark else ColorLight
            ThemeColorPalette.GREEN -> if (darkTheme) ColorGreenDark else ColorGreenDark
        }

    val color = if (animatePaletteChange) animateColors(targetColors) else targetColors

    val typography =
        Typography(
            bold48 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
                lineHeight = 56.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            bold36 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                lineHeight = 42.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            bold16 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            bold14 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            bold12 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            semibold48 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 48.sp,
                lineHeight = 56.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            semibold36 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 36.sp,
                lineHeight = 42.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            semibold16 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            regular48 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.Normal,
                fontSize = 48.sp,
                lineHeight = 56.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            regular14 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 21.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            regular12 =
            TextStyle(
                fontFamily = nunitoFont(),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                letterSpacing = 0.5.sp,
                color = color.textPrimary
            ),
            handwrittenRegular64 =
            TextStyle(
                fontFamily = caveatFont(),
                fontWeight = FontWeight.Normal,
                fontSize = 64.sp,
                lineHeight = 64.sp,
                letterSpacing = 1.sp,
                color = color.textPrimary
            ),
            handwrittenSemibold64 =
            TextStyle(
                fontFamily = caveatFont(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 64.sp,
                lineHeight = 64.sp,
                letterSpacing = 1.sp,
                color = color.textPrimary
            )
        )

    val elevation =
        Elevation(
            small = 4.dp,
            normal = 8.dp,
            large = 12.dp,
            extraLarge = 16.dp
        )

    val radius =
        Radius(
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
            radius64 = 64.dp
        )

    val spacing =
        Spacing(
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
            space128 = 128.dp
        )

    val size =
        Size(
            compactScreenWidth = 328.dp,
            clickableIconArea = 40.dp
        )

    val thickness =
        Thickness(
            thickness1 = 1.dp,
            thickness2 = 2.dp,
            thickness4 = 4.dp
        )

    val aspect =
        AspectRatio(
            productCard = 1.8f,
            recipeCard = 1.8f,
            recipeDetailsImage = 1.2f
        )

    val shape =
        Shape(
            largeRoundedCornerShape = RoundedCornerShape(radius.radius24),
            normalRoundedCornerShape = RoundedCornerShape(radius.radius16)
        )

    CompositionLocalProvider(
        LocalColor provides color,
        LocalTypography provides typography,
        LocalElevation provides elevation,
        LocalShape provides shape,
        LocalSpacing provides spacing,
        LocalSize provides size,
        LocalRadius provides radius,
        LocalThickness provides thickness,
        LocalAspectRatio provides aspect,
        content = content
    )
}

object Theme {
    val color: AppColors
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
    val size: Size
        @Composable
        get() = LocalSize.current
    val radius: Radius
        @Composable
        get() = LocalRadius.current
    val thickness: Thickness
        @Composable
        get() = LocalThickness.current
    val aspect: AspectRatio
        @Composable
        get() = LocalAspectRatio.current
}

@Preview
@Composable
private fun ThemePreview() {
    Theme {
        Box(
            modifier =
            Modifier
                .size(100.dp)
                .background(Theme.color.primary)
        )
    }
}
