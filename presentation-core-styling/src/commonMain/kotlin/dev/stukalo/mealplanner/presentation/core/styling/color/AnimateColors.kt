package dev.stukalo.mealplanner.presentation.core.styling.color

import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.Composable

@Composable
internal fun animateColors(targetColors: AppColors): AppColors = AppColors(
    primary = animateColorAsState(targetColors.primary).value,
    primaryVariant = animateColorAsState(targetColors.primaryVariant).value,
    secondary = animateColorAsState(targetColors.secondary).value,
    background = animateColorAsState(targetColors.background).value,
    backgroundSecondary = animateColorAsState(targetColors.backgroundSecondary).value,
    surfaceVariant = animateColorAsState(targetColors.surfaceVariant).value,
    textPrimary = animateColorAsState(targetColors.textPrimary).value,
    textSecondary = animateColorAsState(targetColors.textSecondary).value,
    textOnPrimary = animateColorAsState(targetColors.textOnPrimary).value,
    textOnPrimaryVariant = animateColorAsState(targetColors.textOnPrimaryVariant).value,
    iconPrimary = animateColorAsState(targetColors.iconPrimary).value,
    iconOnPrimary = animateColorAsState(targetColors.iconOnPrimary).value,
    iconDisabled = animateColorAsState(targetColors.iconDisabled).value,
    error = animateColorAsState(targetColors.error).value,
    warning = animateColorAsState(targetColors.warning).value,
    success = animateColorAsState(targetColors.success).value
)
