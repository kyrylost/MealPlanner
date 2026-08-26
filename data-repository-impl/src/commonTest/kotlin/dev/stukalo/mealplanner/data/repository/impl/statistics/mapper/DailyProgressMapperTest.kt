package dev.stukalo.mealplanner.data.repository.impl.statistics.mapper

import dev.stukalo.mealplanner.data.database.model.progress.DailyProgressDatabaseModel
import dev.stukalo.mealplanner.data.health.model.NutritionHealthModel
import dev.stukalo.mealplanner.domain.model.progress.DailyProgressDomainModel
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class DailyProgressMapperTest {
    private val mapper = DailyProgressMapper()
    private val date = LocalDate(2026, 8, 26)

    @Test
    fun `mapTo correctly maps database model`() {
        val model = DailyProgressDatabaseModel(
            date = date,
            consumedCalories = 1500.0,
            consumedProteins = 100.0,
            consumedFats = 50.0,
            consumedCarbohydrates = 150.0
        )

        val result = mapper.mapTo(model)

        assertEquals(date, result.date)
        assertEquals(1500.0, result.consumedCalories)
        assertEquals(100.0, result.consumedProteins)
        assertEquals(50.0, result.consumedFats)
        assertEquals(150.0, result.consumedCarbohydrates)
    }

    @Test
    fun `mapFromHealth correctly maps health model`() {
        val health = NutritionHealthModel(
            date = date,
            calories = 1800.0,
            proteins = 120.0,
            fats = 60.0,
            carbohydrates = 180.0
        )

        val result = mapper.mapFromHealth(health)

        assertEquals(date, result.date)
        assertEquals(1800.0, result.consumedCalories)
    }

    @Test
    fun `mapToHealth correctly maps domain model`() {
        val domain = DailyProgressDomainModel(
            date = date,
            consumedCalories = 2000.0,
            consumedProteins = 140.0,
            consumedFats = 70.0,
            consumedCarbohydrates = 200.0
        )

        val result = mapper.mapToHealth(domain)

        assertEquals(date, result.date)
        assertEquals(2000.0, result.calories)
    }
}
