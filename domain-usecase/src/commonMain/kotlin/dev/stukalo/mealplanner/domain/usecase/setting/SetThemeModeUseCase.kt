package dev.stukalo.mealplanner.domain.usecase.setting

import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel

/**
 * Use case to update the theme mode preference.
 */
interface SetThemeModeUseCase {
    /**
     * Sets the application theme mode.
     *
     * @param mode The new [ThemeModeDomainModel] to apply.
     */
    suspend operator fun invoke(mode: ThemeModeDomainModel)
}
