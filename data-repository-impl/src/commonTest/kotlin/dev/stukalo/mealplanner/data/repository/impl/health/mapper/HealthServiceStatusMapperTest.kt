package dev.stukalo.mealplanner.data.repository.impl.health.mapper

import dev.stukalo.mealplanner.data.health.model.HealthServiceStatusDataModel
import dev.stukalo.mealplanner.domain.model.health.HealthServiceStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class HealthServiceStatusMapperTest {
    private val mapper = HealthServiceStatusMapper()

    @Test
    fun `mapTo correctly maps all service status types`() {
        val testCases = listOf(
            HealthServiceStatusDataModel.AVAILABLE to HealthServiceStatus.AVAILABLE,
            HealthServiceStatusDataModel.NOT_SUPPORTED to HealthServiceStatus.NOT_SUPPORTED,
            HealthServiceStatusDataModel.NOT_INSTALLED to HealthServiceStatus.NOT_INSTALLED
        )

        testCases.forEach { (data, domain) ->
            assertEquals(domain, mapper.mapTo(data))
        }
    }
}
