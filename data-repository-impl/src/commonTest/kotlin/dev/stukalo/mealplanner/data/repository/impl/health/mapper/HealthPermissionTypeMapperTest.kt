package dev.stukalo.mealplanner.data.repository.impl.health.mapper

import dev.stukalo.mealplanner.data.health.model.HealthPermissionDataModel
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionType
import kotlin.test.Test
import kotlin.test.assertEquals

class HealthPermissionTypeMapperTest {
    private val mapper = HealthPermissionTypeMapper()

    @Test
    fun `mapTo correctly maps all permission types`() {
        val testCases = listOf(
            HealthPermissionDataModel.STEPS_READ to HealthPermissionType.STEPS_READ,
            HealthPermissionDataModel.WEIGHT_READ to HealthPermissionType.WEIGHT_READ,
            HealthPermissionDataModel.WEIGHT_WRITE to HealthPermissionType.WEIGHT_WRITE,
            HealthPermissionDataModel.NUTRITION_READ to HealthPermissionType.NUTRITION_READ,
            HealthPermissionDataModel.NUTRITION_WRITE to HealthPermissionType.NUTRITION_WRITE
        )

        testCases.forEach { (data, domain) ->
            assertEquals(domain, mapper.mapTo(data))
        }
    }
}
