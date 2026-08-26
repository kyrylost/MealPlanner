package dev.stukalo.mealplanner.data.repository.impl.product.mapper

import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCSearchNutrientNetModel
import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCSearchProductNetModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import kotlin.test.Test
import kotlin.test.assertEquals

class FDCSearchProductMapperTest {
    private val mapper = FDCSearchProductMapper()

    @Test
    fun `mapTo correctly maps search product`() {
        val model = FDCSearchProductNetModel(
            fdcId = 67890L,
            description = "Search Product",
            brandOwner = "Search Brand",
            servingSize = 50.0,
            servingSizeUnit = "ml",
            foodNutrients = listOf(
                createNutrient(FdcNutrientIds.PROTEIN_ID, 2.0),
                createNutrient(FdcNutrientIds.CALORIES_ID, 50.0)
            )
        )

        val result = mapper.mapTo(model)

        assertEquals("67890", result.id)
        assertEquals("Search Product", result.productName)
        assertEquals("Search Brand", result.brand)
        assertEquals(50f, result.weight)
        assertEquals(50f, result.calories)
        // (50 * 50) / 100 = 25
        assertEquals(25f, result.caloriesTotal)

        val proteins = result.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }
        assertEquals(2f, proteins?.amount)

        val proteinsTotal = result.nutrientsTotal?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }
        // (2 * 50) / 100 = 1
        assertEquals(1f, proteinsTotal?.amount)
    }

    private fun createNutrient(id: Int, value: Double) = FDCSearchNutrientNetModel(
        nutrientId = id,
        nutrientName = "Test",
        nutrientNumber = "1",
        unitName = "g",
        value = value
    )
}
