package dev.stukalo.mealplanner.domain.usecase.impl.health

import dev.stukalo.mealplanner.domain.service.HealthManager
import dev.stukalo.mealplanner.domain.usecase.health.InstallHealthConnectUseCase

internal class InstallHealthConnectUseCaseImpl(private val healthManager: HealthManager) : InstallHealthConnectUseCase {
    override fun invoke() {
        healthManager.installHealthConnect()
    }
}
