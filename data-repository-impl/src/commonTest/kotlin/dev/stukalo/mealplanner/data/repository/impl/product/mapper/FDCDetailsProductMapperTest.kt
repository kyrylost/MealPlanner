package dev.stukalo.mealplanner.data.repository.impl.product.mapper

import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCProductDetailsNutrientInfoNetModel
import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCProductDetailsNutrientNetModel
import dev.stukalo.mealplanner.data.network.fooddatacentral.model.FDCProductDetailsResponseNetModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FDCDetailsProductMapperTest {
    private val mapper = FDCDetailsProductMapper()

    @Test
    fun `mapTo correctly maps product with all nutrients and serving size`() {
        val model = FDCProductDetailsResponseNetModel(
            fdcId = 12345L,
            description = "Test Product",
            brandOwner = "Test Brand",
            servingSize = 200.0,
            servingSizeUnit = "g",
            ingredients = "Water, Sugar",
            foodNutrients = listOf(
                createNutrient(FdcNutrientIds.PROTEIN_ID, 10.0),
                createNutrient(FdcNutrientIds.CARBS_ID, 20.0),
                createNutrient(FdcNutrientIds.FAT_ID, 5.0),
                createNutrient(FdcNutrientIds.CALORIES_ID, 100.0)
            )
        )

        val result = mapper.mapTo(model)

        assertEquals("12345", result.id)
        assertEquals("Test Product", result.productName)
        assertEquals("Test Brand", result.brand)
        assertEquals("200.0 g", result.servingSize)
        assertEquals("Water, Sugar", result.ingredients)
        assertEquals(100f, result.calories)
        assertEquals(200f, result.weight)

        // Calories total: (100 * 200) / 100 = 200
        assertEquals(200f, result.caloriesTotal)

        val proteins = result.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }
        assertEquals(10f, proteins?.amount)

        val proteinsTotal = result.nutrientsTotal?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }
        // (10 * 200) / 100 = 20
        assertEquals(20f, proteinsTotal?.amount)
    }

    @Test
    fun `mapTo handles null serving size`() {
        val model = FDCProductDetailsResponseNetModel(
            fdcId = 12345L,
            description = "Test Product",
            servingSize = null
        )

        val result = mapper.mapTo(model)

        assertNull(result.weight)
        assertNull(result.caloriesTotal)
        assertNull(result.nutrientsTotal)
        assertNull(result.servingSize)
    }

    private fun createNutrient(id: Int, amount: Double) = FDCProductDetailsNutrientNetModel(
        nutrient = FDCProductDetailsNutrientInfoNetModel(id = id, name = "Test", unitName = "g"),
        amount = amount
    )
}
