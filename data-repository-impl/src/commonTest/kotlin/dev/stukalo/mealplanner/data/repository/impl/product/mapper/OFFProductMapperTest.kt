package dev.stukalo.mealplanner.data.repository.impl.product.mapper

import dev.stukalo.mealplanner.data.network.openfoodfacts.model.OFFNutrimentsNetModel
import dev.stukalo.mealplanner.data.network.openfoodfacts.model.OFFProductNetModel
import dev.stukalo.mealplanner.data.network.openfoodfacts.model.OFFProductResponseNetModel
import dev.stukalo.mealplanner.domain.model.food.quality.NovaGroup
import dev.stukalo.mealplanner.domain.model.food.quality.NutriScore
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import kotlin.test.Test
import kotlin.test.assertEquals

class OFFProductMapperTest {
    private val mapper = OFFProductMapper()

    @Test
    fun `mapTo correctly maps OFF product`() {
        val model = OFFProductResponseNetModel(
            code = "123456789",
            product = OFFProductNetModel(
                productName = "OFF Product",
                brands = "OFF Brand",
                imageUrl = "http://image.com",
                ingredientsText = "Ingredient A, B",
                servingSize = "100g",
                nutriScoreGrade = "a",
                novaGroup = 1,
                nutriments = OFFNutrimentsNetModel(
                    proteins100g = 10.0,
                    carbohydrates100g = 20.0,
                    fat100g = 5.0,
                    energyKcal100g = 150.0
                )
            )
        )

        val result = mapper.mapTo(model)

        assertEquals("123456789", result.id)
        assertEquals("OFF Product", result.productName)
        assertEquals("OFF Brand", result.brand)
        assertEquals("http://image.com", result.imageUrl)
        assertEquals("Ingredient A, B", result.ingredients)
        assertEquals("100g", result.servingSize)
        assertEquals(NutriScore.A, result.nutriScore)
        assertEquals(NovaGroup.GROUP_1, result.novaGroup)
        assertEquals(150f, result.calories)

        val proteins = result.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }
        assertEquals(10f, proteins?.amount)
    }
}
