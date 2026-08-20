package dev.stukalo.mealplanner.presentation.feature.onboarding.di

import dev.stukalo.mealplanner.presentation.feature.onboarding.screen.OnboardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin module for the Onboarding feature.
 */
val onboardingModule = module {
    viewModelOf(::OnboardingViewModel)
}
