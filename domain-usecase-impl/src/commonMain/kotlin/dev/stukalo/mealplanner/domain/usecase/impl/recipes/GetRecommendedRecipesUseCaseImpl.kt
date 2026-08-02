package dev.stukalo.mealplanner.domain.usecase.impl.recipes

import androidx.paging.PagingData
import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import dev.stukalo.mealplanner.domain.model.recipe.RecipeDomainModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyNormUseCase
import dev.stukalo.mealplanner.domain.usecase.nutrition.GetDailyProgressUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.recipes.GetRecommendedRecipesUseCase
import dev.stukalo.mealplanner.domain.usecase.slot.GetMealScheduleUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

class GetRecommendedRecipesUseCaseImpl(
    private val getMealScheduleUseCase: GetMealScheduleUseCase,
    private val getDailyNormUseCase: GetDailyNormUseCase,
    private val getDailyProgressUseCase: GetDailyProgressUseCase,
    private val getRecipesUseCase: GetRecipesUseCase,
    private val clock: Clock
) : GetRecommendedRecipesUseCase {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun invoke(slot: MealSlotDomainModel?): Flow<PagingData<RecipeDomainModel>> {
        return getMealScheduleUseCase().flatMapLatest { slots ->
            val now = clock.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val today = now.date
            val currentTime = now.time

            combine(
                getDailyNormUseCase(),
                getDailyProgressUseCase(today)
            ) { norm, progress ->
                DataPool(slots, norm, progress, currentTime)
            }.flatMapLatest { data ->
                val norm = data.norm ?: return@flatMapLatest flowOf(PagingData.empty())

                val activeSlot = slot ?: findActiveSlot(data.slots, data.currentTime)
                if (activeSlot == null) return@flatMapLatest flowOf(PagingData.empty())

                val unconsumedSlots = data.slots.filter { !it.isConsumed }

                val pPool =
                    if (unconsumedSlots.isEmpty()) {
                        activeSlot.proteinsPercentage.toDouble()
                    } else {
                        unconsumedSlots
                            .sumOf {
                                it.proteinsPercentage
                            }.toDouble()
                    }
                val fPool =
                    if (unconsumedSlots.isEmpty()) {
                        activeSlot.fatsPercentage.toDouble()
                    } else {
                        unconsumedSlots
                            .sumOf { it.fatsPercentage }
                            .toDouble()
                    }
                val cPool =
                    if (unconsumedSlots.isEmpty()) {
                        activeSlot.carbsPercentage.toDouble()
                    } else {
                        unconsumedSlots
                            .sumOf {
                                it.carbsPercentage
                            }.toDouble()
                    }

                val remainingProteins = maxOf(
                    0.0,
                    norm.proteins - (data.progress?.consumedProteins ?: 0.0)
                )
                val remainingFats = maxOf(0.0, norm.fats - (data.progress?.consumedFats ?: 0.0))
                val remainingCarbs = maxOf(
                    0.0,
                    norm.carbohydrates - (data.progress?.consumedCarbohydrates ?: 0.0)
                )

                val targetProteins = remainingProteins * (activeSlot.proteinsPercentage / pPool)
                val targetFats = remainingFats * (activeSlot.fatsPercentage / fPool)
                val targetCarbs = remainingCarbs * (activeSlot.carbsPercentage / cPool)
                val targetCalories = (targetProteins * 4) + (targetFats * 9) + (targetCarbs * 4)

                getRecipesUseCase(
                    calories =
                    (targetCalories.toInt() - CALORIE_TOLERANCE)..(targetCalories.toInt() + CALORIE_TOLERANCE),
                    proteins =
                    (targetProteins.toInt() - MACRO_TOLERANCE)..(targetProteins.toInt() + MACRO_TOLERANCE),
                    fats =
                    (targetFats.toInt() - MACRO_TOLERANCE)..(targetFats.toInt() + MACRO_TOLERANCE),
                    carbohydrates =
                    (targetCarbs.toInt() - MACRO_TOLERANCE)..(targetCarbs.toInt() + MACRO_TOLERANCE),
                    mealTypes = activeSlot.mealTypes
                )
            }
        }
    }

    private data class DataPool(
        val slots: List<MealSlotDomainModel>,
        val norm: DailyNormDomainModel?,
        val progress: DailyProgressDomainModel?,
        val currentTime: LocalTime
    )

    private fun findActiveSlot(slots: List<MealSlotDomainModel>, currentTime: LocalTime): MealSlotDomainModel? {
        val sorted = slots.sortedBy { it.startTime }
        return sorted.findLast { it.startTime <= currentTime } ?: sorted.lastOrNull()
    }

    companion object {
        private const val CALORIE_TOLERANCE = 100
        private const val MACRO_TOLERANCE = 5
    }
}
