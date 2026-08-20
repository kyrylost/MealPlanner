package dev.stukalo.mealplanner.domain.usecase.setting

import kotlinx.coroutines.flow.Flow

/**
 * Use case for checking if onboarding has been shown.
 */
interface IsOnboardingShownUseCase {
    operator fun invoke(): Flow<Boolean>
}
