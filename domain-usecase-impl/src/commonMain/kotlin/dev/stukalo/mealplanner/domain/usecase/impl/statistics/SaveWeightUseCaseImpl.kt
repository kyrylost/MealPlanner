package dev.stukalo.mealplanner.domain.usecase.impl.statistics

import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import dev.stukalo.mealplanner.domain.repository.HealthRepository
import dev.stukalo.mealplanner.domain.repository.WeightRepository
import dev.stukalo.mealplanner.domain.usecase.statistics.SaveWeightUseCase
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * Implementation of [SaveWeightUseCase] that saves the current weight for today.
 *
 * @property weightRepository Repository to persist weight data.
 * @property healthRepository Repository to sync data with health services.
 * @property clock Clock provider for today's date calculation.
 */
class SaveWeightUseCaseImpl(
    private val weightRepository: WeightRepository,
    private val healthRepository: HealthRepository,
    private val clock: Clock
) : SaveWeightUseCase {
    override suspend fun invoke(weight: Double): Result<Unit> {
        val today = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val model = WeightHistoryDomainModel(
            date = today,
            weight = weight
        )
        return weightRepository.saveWeight(model).onSuccess {
            healthRepository.writeWeight(model)
        }
    }
}
