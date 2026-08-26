package dev.stukalo.mealplanner.data.repository.impl.statistics.mapper

import dev.stukalo.mealplanner.data.database.model.norm.DailyNormDatabaseModel
import dev.stukalo.mealplanner.domain.model.norm.DailyNormDomainModel
import kotlin.test.Test
import kotlin.test.assertEquals

class DailyNormMapperTest {
    private val mapper = DailyNormMapper()

    @Test
    fun `mapTo correctly maps database model to domain model`() {
        val model = DailyNormDatabaseModel(
            calories = 2000.0,
            proteins = 150.0,
            fats = 60.0,
            carbohydrates = 200.0
        )

        val result = mapper.mapTo(model)

        assertEquals(2000.0, result.calories)
        assertEquals(150.0, result.proteins)
        assertEquals(60.0, result.fats)
        assertEquals(200.0, result.carbohydrates)
    }

    @Test
    fun `mapFrom correctly maps domain model to database model`() {
        val domain = DailyNormDomainModel(
            calories = 2500.0,
            proteins = 180.0,
            fats = 80.0,
            carbohydrates = 250.0
        )

        val result = mapper.mapFrom(domain)

        assertEquals(2500.0, result.calories)
        assertEquals(180.0, result.proteins)
        assertEquals(80.0, result.fats)
        assertEquals(250.0, result.carbohydrates)
    }
}
