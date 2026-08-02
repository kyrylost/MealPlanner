package dev.stukalo.mealplanner.presentation.core.styling.dimension

import androidx.compose.runtime.compositionLocalOf

val LocalAspectRatio =
    compositionLocalOf<AspectRatio> {
        error("No AspectRatio provided")
    }
