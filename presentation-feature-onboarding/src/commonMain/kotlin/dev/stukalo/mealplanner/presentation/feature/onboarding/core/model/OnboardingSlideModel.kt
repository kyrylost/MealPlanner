package dev.stukalo.mealplanner.presentation.feature.onboarding.core.model

import org.jetbrains.compose.resources.StringResource

/**
 * Data model for an onboarding slide.
 *
 * @property title The title of the slide.
 * @property description The description text of the slide.
 */
internal data class OnboardingSlideModel(
    val title: StringResource,
    val description: StringResource,
    val animationPath: String? = null
)
