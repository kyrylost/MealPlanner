package dev.stukalo.mealplanner.data.repository.impl.slot.mapper

import dev.stukalo.mealplanner.data.database.model.slot.MealTypeDatabaseModel
import dev.stukalo.mealplanner.domain.model.recipe.MealTypeDomainModel
import kotlin.test.Test
import kotlin.test.assertEquals

class MealTypeMapperTest {
    private val mapper = MealTypeMapper()

    @Test
    fun `mapTo correctly maps all meal types`() {
        val testCases = listOf(
            MealTypeDatabaseModel.BREAKFAST to MealTypeDomainModel.BREAKFAST,
            MealTypeDatabaseModel.LUNCH to MealTypeDomainModel.LUNCH,
            MealTypeDatabaseModel.DINNER to MealTypeDomainModel.DINNER,
            MealTypeDatabaseModel.SNACK to MealTypeDomainModel.SNACK,
            MealTypeDatabaseModel.TEATIME to MealTypeDomainModel.TEATIME
        )

        testCases.forEach { (data, domain) ->
            assertEquals(domain, mapper.mapTo(data))
        }
    }

    @Test
    fun `mapFrom correctly maps all meal types`() {
        val testCases = listOf(
            MealTypeDomainModel.BREAKFAST to MealTypeDatabaseModel.BREAKFAST,
            MealTypeDomainModel.LUNCH to MealTypeDatabaseModel.LUNCH,
            MealTypeDomainModel.DINNER to MealTypeDatabaseModel.DINNER,
            MealTypeDomainModel.SNACK to MealTypeDatabaseModel.SNACK,
            MealTypeDomainModel.TEATIME to MealTypeDatabaseModel.TEATIME
        )

        testCases.forEach { (domain, data) ->
            assertEquals(data, mapper.mapFrom(domain))
        }
    }
}
