package dev.stukalo.mealplanner.presentation.core.styling.color.anim

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable
import dev.stukalo.mealplanner.presentation.core.styling.color.model.AppColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.BackgroundColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.BrandColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.IconColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.QualityColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.StateColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.SurfaceColors
import dev.stukalo.mealplanner.presentation.core.styling.color.model.TextColors

/**
 * Animates the transition between two [AppColors] states.
 *
 * @param targetColors The target [AppColors] to animate towards.
 * @return A new [AppColors] instance with animated color values.
 */
@Composable
internal fun animateColors(targetColors: AppColors): AppColors = AppColors(
    brand = targetColors.brand.animate(),
    text = targetColors.text.animate(),
    icon = targetColors.icon.animate(),
    background = targetColors.background.animate(),
    surface = targetColors.surface.animate(),
    state = targetColors.state.animate(),
    quality = targetColors.quality.animate()
)

@Composable
private fun BrandColors.animate() = BrandColors(
    primary = animateColorAsState(primary).value,
    primaryVariant = animateColorAsState(primaryVariant).value,
    secondary = animateColorAsState(secondary).value
)

@Composable
private fun TextColors.animate() = TextColors(
    primary = animateColorAsState(primary).value,
    secondary = animateColorAsState(secondary).value,
    onPrimary = animateColorAsState(onPrimary).value,
    onPrimaryVariant = animateColorAsState(onPrimaryVariant).value
)

@Composable
private fun IconColors.animate() = IconColors(
    primary = animateColorAsState(primary).value,
    onPrimary = animateColorAsState(onPrimary).value,
    disabled = animateColorAsState(disabled).value
)

@Composable
private fun BackgroundColors.animate() = BackgroundColors(
    primary = animateColorAsState(primary).value,
    secondary = animateColorAsState(secondary).value
)

@Composable
private fun SurfaceColors.animate() = SurfaceColors(
    variant = animateColorAsState(variant).value
)

@Composable
private fun StateColors.animate() = StateColors(
    fixedLight = animateColorAsState(fixedLight).value,
    fixedDark = animateColorAsState(fixedDark).value,
    error = animateColorAsState(error).value,
    warning = animateColorAsState(warning).value,
    success = animateColorAsState(success).value
)

@Composable
private fun QualityColors.animate() = QualityColors(
    nutriScoreA = animateColorAsState(nutriScoreA).value,
    nutriScoreB = animateColorAsState(nutriScoreB).value,
    nutriScoreC = animateColorAsState(nutriScoreC).value,
    nutriScoreD = animateColorAsState(nutriScoreD).value,
    nutriScoreE = animateColorAsState(nutriScoreE).value,
    novaGroup1 = animateColorAsState(novaGroup1).value,
    novaGroup2 = animateColorAsState(novaGroup2).value,
    novaGroup3 = animateColorAsState(novaGroup3).value,
    novaGroup4 = animateColorAsState(novaGroup4).value
)
