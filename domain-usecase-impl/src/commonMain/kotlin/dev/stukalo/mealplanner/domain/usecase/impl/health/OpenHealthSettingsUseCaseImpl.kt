package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.service.HealthManager
import dev.stukalo.mealplanner.domain.usecase.health.OpenHealthSettingsUseCase

internal class OpenHealthSettingsUseCaseImpl(private val healthManager: HealthManager) : OpenHealthSettingsUseCase {
    override fun invoke() {
        healthManager.openHealthSettings()
    }
}
