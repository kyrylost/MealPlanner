package dev.stukalo.mealplanner.domain.usecase.impl.setting

import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.usecase.setting.GetLocaleUseCase
import kotlinx.coroutines.flow.Flow

/**
 * Use case to get the current locale setting.
 */
class GetLocaleUseCaseImpl(private val settingsRepository: SettingsRepository) : GetLocaleUseCase {
    override fun invoke(): Flow<String?> = settingsRepository.getLocale()
}
