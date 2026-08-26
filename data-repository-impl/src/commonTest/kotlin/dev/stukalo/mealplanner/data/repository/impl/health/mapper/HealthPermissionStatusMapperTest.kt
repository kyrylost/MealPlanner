package dev.stukalo.mealplanner.data.repository.impl.health.mapper

import dev.stukalo.mealplanner.data.health.HealthDataSource
import dev.stukalo.mealplanner.data.health.model.HealthPermissionStatusDataModel
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionGroup
import dev.stukalo.mealplanner.domain.model.health.HealthPermissionStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class HealthPermissionStatusMapperTest {
    private val mapper = HealthPermissionStatusMapper()

    @Test
    fun `mapTo correctly maps all valid permission groups`() {
        val testCases = listOf(
            HealthDataSource.ID_STEPS to HealthPermissionGroup.STEPS,
            HealthDataSource.ID_WEIGHT_READ to HealthPermissionGroup.WEIGHT_READ,
            HealthDataSource.ID_WEIGHT_WRITE to HealthPermissionGroup.WEIGHT_WRITE,
            HealthDataSource.ID_NUTRITION_READ to HealthPermissionGroup.NUTRITION_READ,
            HealthDataSource.ID_NUTRITION_WRITE to HealthPermissionGroup.NUTRITION_WRITE,
            HealthDataSource.ID_INTEGRATED to HealthPermissionGroup.INTEGRATED
        )

        testCases.forEach { (id, group) ->
            val dataModel = HealthPermissionStatusDataModel(id = id, isGranted = true)
            val expectedDomainModel = HealthPermissionStatus(group = group, isGranted = true)

            assertEquals(expectedDomainModel, mapper.mapTo(dataModel))
        }
    }

    @Test
    fun `mapTo throws exception for unknown ID`() {
        val dataModel = HealthPermissionStatusDataModel(id = "unknown", isGranted = true)

        assertFailsWith<IllegalArgumentException> {
            mapper.mapTo(dataModel)
        }
    }

    @Test
    fun `mapGroupToId correctly maps all domain permission groups`() {
        val testCases = listOf(
            HealthPermissionGroup.STEPS to HealthDataSource.ID_STEPS,
            HealthPermissionGroup.WEIGHT_READ to HealthDataSource.ID_WEIGHT_READ,
            HealthPermissionGroup.WEIGHT_WRITE to HealthDataSource.ID_WEIGHT_WRITE,
            HealthPermissionGroup.NUTRITION_READ to HealthDataSource.ID_NUTRITION_READ,
            HealthPermissionGroup.NUTRITION_WRITE to HealthDataSource.ID_NUTRITION_WRITE,
            HealthPermissionGroup.INTEGRATED to HealthDataSource.ID_INTEGRATED
        )

        testCases.forEach { (group, expectedId) ->
            assertEquals(expectedId, mapper.mapGroupToId(group))
        }
    }
}
