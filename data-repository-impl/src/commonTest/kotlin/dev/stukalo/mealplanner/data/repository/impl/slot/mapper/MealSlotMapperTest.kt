package dev.stukalo.mealplanner.data.repository.impl.slot.mapper

import dev.stukalo.mealplanner.data.database.model.slot.MealSlotDatabaseModel
import dev.stukalo.mealplanner.data.database.model.slot.MealTypeDatabaseModel
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import dev.stukalo.mealplanner.domain.model.slot.MealSlotDomainModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Instant

class MealSlotMapperTest {
    private val mealTypeMapper = MealTypeMapper()

    private class FakeClock(var currentInstant: Instant) : Clock {
        override fun now(): Instant = currentInstant
    }

    @Test
    fun `mapTo correctly maps consumed slot`() {
        val now = Instant.parse("2026-08-26T12:00:00Z")
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val clock = FakeClock(now)
        val mapper = MealSlotMapper(mealTypeMapper, clock)

        val model = MealSlotDatabaseModel(
            id = 1,
            startTime = LocalTime(8, 0),
            proteinsPercentage = 30,
            fatsPercentage = 30,
            carbsPercentage = 40,
            mealType = MealTypeDatabaseModel.BREAKFAST,
            lastConsumedDate = today
        )

        val result = mapper.mapTo(model)

        assertEquals(1, result.id)
        assertEquals(LocalTime(8, 0), result.startTime)
        assertEquals(MealTypeDomainModel.BREAKFAST, result.mealType)
        assertTrue(result.isConsumed)
    }

    @Test
    fun `mapTo correctly maps non-consumed slot`() {
        val now = Instant.parse("2026-08-26T12:00:00Z")
        val yesterday = LocalDate(2026, 8, 25)
        val clock = FakeClock(now)
        val mapper = MealSlotMapper(mealTypeMapper, clock)

        val model = MealSlotDatabaseModel(
            id = 1,
            startTime = LocalTime(8, 0),
            proteinsPercentage = 30,
            fatsPercentage = 30,
            carbsPercentage = 40,
            mealType = MealTypeDatabaseModel.BREAKFAST,
            lastConsumedDate = yesterday
        )

        val result = mapper.mapTo(model)

        assertFalse(result.isConsumed)
    }

    @Test
    fun `mapFrom correctly maps domain model`() {
        val clock = FakeClock(Instant.DISTANT_PAST)
        val mapper = MealSlotMapper(mealTypeMapper, clock)

        val domain = MealSlotDomainModel(
            id = 1,
            startTime = LocalTime(8, 0),
            proteinsPercentage = 30,
            fatsPercentage = 30,
            carbsPercentage = 40,
            mealType = MealTypeDomainModel.BREAKFAST,
            isConsumed = false
        )

        val result = mapper.mapFrom(domain)

        assertEquals(1, result.id)
        assertEquals(LocalTime(8, 0), result.startTime)
        assertEquals(MealTypeDatabaseModel.BREAKFAST, result.mealType)
        assertEquals(null, result.lastConsumedDate)
    }
}
