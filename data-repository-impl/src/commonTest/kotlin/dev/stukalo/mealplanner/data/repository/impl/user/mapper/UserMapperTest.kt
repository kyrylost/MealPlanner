package dev.stukalo.mealplanner.data.repository.impl.user.mapper

import dev.stukalo.mealplanner.data.database.model.user.ActivityLevelDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.DietDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.GenderDatabaseModel
import dev.stukalo.mealplanner.data.database.model.user.UserDatabaseModel
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.domain.model.user.UserDomainModel
import kotlinx.datetime.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals

class UserMapperTest {
    private val mapper = UserMapper()
    private val birthDate = LocalDate(1990, 1, 1)

    @Test
    fun `mapTo correctly maps database model and weight`() {
        val model = UserDatabaseModel(
            id = 1L,
            name = "John Doe",
            birthDate = birthDate,
            height = 180.0,
            targetWeight = 75.0,
            physicalActivity = ActivityLevelDatabaseModel.MEDIUM,
            gender = GenderDatabaseModel.MALE,
            diet = DietDatabaseModel.BALANCED_DIET,
            stepsTarget = 10000
        )

        val result = mapper.mapTo(model, weight = 80.0)

        assertEquals(1L, result.id)
        assertEquals("John Doe", result.name)
        assertEquals(birthDate, result.birthDate)
        assertEquals(180.0, result.height)
        assertEquals(80.0, result.weight)
        assertEquals(75.0, result.targetWeight)
        assertEquals(ActivityLevelDomainModel.MEDIUM, result.physicalActivity)
        assertEquals(GenderDomainModel.MALE, result.gender)
        assertEquals(DietDomainModel.BALANCED_DIET, result.diet)
        assertEquals(10000, result.stepsTarget)
    }

    @Test
    fun `mapFrom correctly maps domain model`() {
        val domain = UserDomainModel(
            id = 1L,
            name = "Jane Doe",
            birthDate = birthDate,
            height = 165.0,
            weight = 60.0,
            targetWeight = 55.0,
            physicalActivity = ActivityLevelDomainModel.HIGH,
            gender = GenderDomainModel.FEMALE,
            diet = DietDomainModel.WEIGHT_LOSS,
            stepsTarget = 12000
        )

        val result = mapper.mapFrom(domain)

        assertEquals(1L, result.id)
        assertEquals("Jane Doe", result.name)
        assertEquals(ActivityLevelDatabaseModel.HIGH, result.physicalActivity)
        assertEquals(GenderDatabaseModel.FEMALE, result.gender)
        assertEquals(DietDatabaseModel.WEIGHT_LOSS, result.diet)
    }
}
