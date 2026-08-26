package dev.stukalo.mealplanner.domain.usecase.impl.slot

import dev.stukalo.mealplanner.domain.repository.MealSlotRepository
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.service.NotificationScheduler
import dev.stukalo.mealplanner.domain.usecase.slot.SyncMealRemindersUseCase
import kotlinx.coroutines.flow.first

/**
 * Implementation of [SyncMealRemindersUseCase].
 * Orchestrates the scheduling of meal reminders based on user settings and meal slots.
 */
internal class SyncMealRemindersUseCaseImpl(
    private val mealSlotRepository: MealSlotRepository,
    private val settingsRepository: SettingsRepository,
    private val notificationScheduler: NotificationScheduler
) : SyncMealRemindersUseCase {

    override suspend fun invoke(): Result<Unit> = try {
        val enabled = settingsRepository.isMealRemindersEnabled().first()
        notificationScheduler.cancelAllReminders()

        if (enabled) {
            val slots = mealSlotRepository.getMealSlotsAsFlow().first()
            slots.forEach { slot ->
                notificationScheduler.scheduleMealReminder(
                    id = slot.id,
                    hour = slot.startTime.hour,
                    minute = slot.startTime.minute
                )
            }
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
