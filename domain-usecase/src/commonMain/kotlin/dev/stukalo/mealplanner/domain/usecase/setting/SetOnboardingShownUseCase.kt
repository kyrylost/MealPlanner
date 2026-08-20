package dev.stukalo.mealplanner.domain.usecase.setting

/**
 * Use case for marking onboarding as shown.
 */
interface SetOnboardingShownUseCase {
    suspend operator fun invoke(shown: Boolean)
}
