package dev.stukalo.mealplanner.domain.usecase.impl.user

import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Instant

class CalculateDailyNormUseCaseTest {

    private val fixedClock = object : Clock {
        override fun now(): Instant = Instant.fromEpochMilliseconds(1767225600000L) // 2026-01-01
    }

    private val useCase = CalculateDailyNormUseCaseImpl(fixedClock)

    @Test
    fun `calculate norm for young male with balanced diet`() {
        val user = UserDomainModel(
            id = 1,
            name = "John",
            birthDate = LocalDate(2000, 1, 1),
            height = 180.0,
            weight = 80.0,
            targetWeight = 80.0,
            physicalActivity = ActivityLevelDomainModel.MEDIUM,
            gender = GenderDomainModel.MALE,
            diet = DietDomainModel.BALANCED_DIET,
            stepsTarget = 10000
        )

        val result = useCase(user)
        assertTrue(result.calories > 0)
    }
}
