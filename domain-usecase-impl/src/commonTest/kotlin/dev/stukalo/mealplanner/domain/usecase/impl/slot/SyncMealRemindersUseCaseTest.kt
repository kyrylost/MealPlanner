package dev.stukalo.mealplanner.domain.usecase.impl.slot

import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import dev.stukalo.mealplanner.domain.repository.MealScheduleRepository
import dev.stukalo.mealplanner.domain.repository.SettingsRepository
import dev.stukalo.mealplanner.domain.service.NotificationScheduler
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class SyncMealRemindersUseCaseTest {

    private lateinit var useCase: SyncMealRemindersUseCaseImpl
    private val mealScheduleRepository = MockMealScheduleRepository()
    private val settingsRepository = MockSettingsRepository()
    private val notificationScheduler = MockNotificationScheduler()

    @BeforeTest
    fun setup() {
        useCase = SyncMealRemindersUseCaseImpl(
            mealScheduleRepository,
            settingsRepository,
            notificationScheduler
        )
    }

    @Test
    fun `should cancel all reminders and schedule new ones when enabled`() = runTest {
        settingsRepository.mealRemindersEnabled = true
        mealScheduleRepository.slots = listOf(
            MealSlotDomainModel(1, LocalTime(8, 0), 25, 25, 50, MealTypeDomainModel.BREAKFAST, false),
            MealSlotDomainModel(2, LocalTime(13, 0), 25, 25, 50, MealTypeDomainModel.LUNCH, false)
        )

        val result = useCase()

        assertTrue(result.isSuccess)
        assertTrue(notificationScheduler.cancelCalled)
        assertTrue(notificationScheduler.scheduledIds.contains(1))
        assertTrue(notificationScheduler.scheduledIds.contains(2))
    }

    @Test
    fun `should only cancel reminders when disabled`() = runTest {
        settingsRepository.mealRemindersEnabled = false

        val result = useCase()

        assertTrue(result.isSuccess)
        assertTrue(notificationScheduler.cancelCalled)
        assertTrue(notificationScheduler.scheduledIds.isEmpty())
    }

    private class MockMealScheduleRepository : MealScheduleRepository {
        var slots = emptyList<MealSlotDomainModel>()
        override fun getMealSlotsAsFlow() = flowOf(slots)
        override suspend fun updateConsumedStatus(id: Int, isConsumed: Boolean) = Result.success(Unit)
        override suspend fun updateSlotTime(id: Int, startTime: LocalTime) = Result.success(Unit)
        override suspend fun resetDailyConsumedStatus() = Result.success(Unit)
    }

    private class MockSettingsRepository : SettingsRepository {
        var mealRemindersEnabled = false
        override fun isMealRemindersEnabled() = flowOf(mealRemindersEnabled)
        override suspend fun setMealRemindersEnabled(enabled: Boolean) {
            mealRemindersEnabled = enabled
        }

        override fun getColorPalette() = throw NotImplementedError()
        override suspend fun setColorPalette(
            palette: dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
        ) {}
        override fun getThemeMode() = throw NotImplementedError()
        override suspend fun setThemeMode(mode: dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel) {}
        override fun getLocale() = throw NotImplementedError()
        override suspend fun setLocale(locale: String) {}
        override fun isOnboardingShown() = throw NotImplementedError()
        override suspend fun setOnboardingShown(shown: Boolean) {}
    }

    private class MockNotificationScheduler : NotificationScheduler {
        var cancelCalled = false
        val scheduledIds = mutableSetOf<Int>()

        override suspend fun scheduleMealReminder(id: Int, hour: Int, minute: Int) {
            scheduledIds.add(id)
        }

        override fun cancelAllReminders() {
            cancelCalled = true
        }

        override fun hasPermission(): Boolean = true
    }
}
