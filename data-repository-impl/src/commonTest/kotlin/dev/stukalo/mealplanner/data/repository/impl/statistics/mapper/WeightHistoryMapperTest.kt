package dev.stukalo.mealplanner.data.repository.impl.statistics.mapper

import dev.stukalo.mealplanner.data.database.model.statistics.WeightHistoryDatabaseModel
import dev.stukalo.mealplanner.data.health.model.WeightHealthModel
import dev.stukalo.mealplanner.domain.model.statistics.WeightHistoryDomainModel
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class WeightHistoryMapperTest {
    private val mapper = WeightHistoryMapper()
    private val date = LocalDate(2026, 8, 26)

    @Test
    fun `mapTo correctly maps database model`() {
        val model = WeightHistoryDatabaseModel(date = date, weight = 80.5)
        val result = mapper.mapTo(model)
        assertEquals(date, result.date)
        assertEquals(80.5, result.weight)
    }

    @Test
    fun `mapFromHealth correctly maps health model`() {
        val health = WeightHealthModel(date = date, weight = 81.0)
        val result = mapper.mapFromHealth(health)
        assertEquals(date, result.date)
        assertEquals(81.0, result.weight)
    }

    @Test
    fun `mapToHealth correctly maps domain model`() {
        val domain = WeightHistoryDomainModel(date = date, weight = 79.5)
        val result = mapper.mapToHealth(domain)
        assertEquals(date, result.date)
        assertEquals(79.5, result.weight)
    }

    @Test
    fun `mapListTo correctly maps list`() {
        val list = listOf(
            WeightHistoryDatabaseModel(date = date, weight = 80.0),
            WeightHistoryDatabaseModel(date = LocalDate(2026, 8, 25), weight = 81.0)
        )
        val result = mapper.mapListTo(list)
        assertEquals(2, result.size)
        assertEquals(80.0, result[0].weight)
        assertEquals(81.0, result[1].weight)
    }
}
