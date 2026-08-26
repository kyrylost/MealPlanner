package dev.stukalo.mealplanner.domain.usecase.impl.slot

import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.repository.MealSlotRepository
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.UpdateDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.TrackMealConsumedUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class TrackMealConsumedUseCaseImpl(
    private val getDailyProgressUseCase: GetDailyProgressUseCase,
    private val updateDailyProgressUseCase: UpdateDailyProgressUseCase,
    private val mealSlotRepository: MealSlotRepository,
    private val clock: Clock
) : TrackMealConsumedUseCase {
    override suspend fun invoke(
        slotId: Int,
        calories: Double,
        proteins: Double,
        fats: Double,
        carbohydrates: Double
    ): Result<Unit> {
        val now = clock.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val today = now.date

        // 1. Update Daily Progress
        val currentProgress =
            getDailyProgressUseCase(today).firstOrNull() ?: DailyProgressDomainModel(
                date = today,
                consumedCalories = 0.0,
                consumedProteins = 0.0,
                consumedFats = 0.0,
                consumedCarbohydrates = 0.0
            )

        val updatedProgress =
            currentProgress.copy(
                consumedCalories = currentProgress.consumedCalories + calories,
                consumedProteins = currentProgress.consumedProteins + proteins,
                consumedFats = currentProgress.consumedFats + fats,
                consumedCarbohydrates = currentProgress.consumedCarbohydrates + carbohydrates
            )

        updateDailyProgressUseCase(updatedProgress)

        // 2. Mark Slot as Consumed
        return mealSlotRepository.updateConsumedStatus(slotId, true)
    }
}
