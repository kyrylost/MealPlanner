package dev.stukalo.mealplanner.domain.usecase.setting

import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Use case to retrieve the current theme mode preference.
 */
interface GetThemeModeUseCase {
    /**
     * Returns a [Flow] that emits the current [ThemeModeDomainModel].
     */
    operator fun invoke(): Flow<ThemeModeDomainModel>
}
