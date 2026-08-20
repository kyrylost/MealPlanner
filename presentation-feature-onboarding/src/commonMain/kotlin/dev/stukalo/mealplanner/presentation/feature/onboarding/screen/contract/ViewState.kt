package dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract

import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.contract.MviViewState
import dev.stukalo.mealplanner.presentation.feature.onboarding.core.model.OnboardingSlideModel

/**
 * The view state for the Onboarding screen.
 *
 * @property slides The list of slides to display.
 * @property currentSlideIndex The index of the current slide.
 */
internal data class ViewState(val slides: List<OnboardingSlideModel> = emptyList(), val currentSlideIndex: Int = 0) :
    MviViewState
