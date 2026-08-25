package dev.stukalo.mealplanner.presentation.feature.onboarding.screen

import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_desc
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_title
import dev.stukalo.mealplanner.core.localization.onboarding_slide2_desc
import dev.stukalo.mealplanner.core.localization.onboarding_slide2_title
import dev.stukalo.mealplanner.core.localization.onboarding_slide3_desc
import dev.stukalo.mealplanner.core.localization.onboarding_slide3_title
import dev.stukalo.mealplanner.domain.usecase.setting.SetOnboardingShownUseCase
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.BaseMviViewModel
import dev.stukalo.mealplanner.presentation.feature.onboarding.core.model.OnboardingSlideModel
import dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract.PartialStateChange
import dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract.ViewState

/**
 * ViewModel for the Onboarding screen.
 * Manages slides and navigation to the next screen.
 *
 * @param setOnboardingShownUseCase Use case for marking onboarding as shown.
 */
internal class OnboardingViewModel(private val setOnboardingShownUseCase: SetOnboardingShownUseCase) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEvent>() {
    override val initialState =
        ViewState(
            slides =
            listOf(
                OnboardingSlideModel(
                    title = Res.string.onboarding_slide1_title,
                    description = Res.string.onboarding_slide1_desc,
                    animationPath = "files/calculate.json"
                ),
                OnboardingSlideModel(
                    title = Res.string.onboarding_slide2_title,
                    description = Res.string.onboarding_slide2_desc,
                    animationPath = "files/cooc.json"
                ),
                OnboardingSlideModel(
                    title = Res.string.onboarding_slide3_title,
                    description = Res.string.onboarding_slide3_desc,
                    animationPath = "files/achieve.json"
                )
            )
        )

    override suspend fun processIntent(intent: ViewIntent) {
        when (intent) {
            ViewIntent.OnSkipClick -> finishOnboarding()
            ViewIntent.OnNextClick -> handleNextClick()
            is ViewIntent.OnSlideChange -> {
                reduce(PartialStateChange.SlideChange(intent.index))
            }
        }
    }

    private fun handleNextClick() {
        val currentIndex = viewState.value.currentSlideIndex
        val totalSlides = viewState.value.slides.size

        if (currentIndex < totalSlides - 1) {
            val nextIndex = currentIndex + 1
            reduce(PartialStateChange.SlideChange(nextIndex))
        } else {
            finishOnboarding()
        }
    }

    private fun finishOnboarding() {
        safeLaunch {
            setOnboardingShownUseCase(true)
            sendEvent(ViewEvent.NavigateToWelcome)
        }
    }
}
