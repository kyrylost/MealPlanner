package dev.stukalo.mealplanner.data.repository.impl.recipe.mapper

import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamNutrientNetModel
import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamNutrientsNetModel
import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamRecipeNetModel
import dev.stukalo.mealplanner.domain.model.nutrient.NutrientTypeDomainModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EdamamRecipeToProductMapperTest {
    private val mapper = EdamamRecipeToProductMapper()

    @Test
    fun `mapTo correctly maps Edamam recipe to product`() {
        val model = EdamamRecipeNetModel(
            uri = "recipe_id_123",
            label = "Recipe Label",
            image = "http://image.url",
            totalWeight = 500.0,
            calories = 1000.0,
            totalNutrients = EdamamNutrientsNetModel(
                protein = EdamamNutrientNetModel(quantity = 50.0),
                carbs = EdamamNutrientNetModel(quantity = 100.0),
                fat = EdamamNutrientNetModel(quantity = 25.0)
            )
        )

        val result = mapper.mapTo(model)

        assertEquals("123", result.id)
        assertEquals("Recipe Label", result.productName)
        assertEquals("http://image.url", result.imageUrl)
        assertEquals(500f, result.weight)
        assertEquals(1000f, result.caloriesTotal)

        // Calories per 100g: (1000 / 500) * 100 = 200
        assertEquals(200f, result.calories)

        val proteinsTotal = result.nutrientsTotal?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }
        assertEquals(50f, proteinsTotal?.amount)

        val proteinsPer100g = result.nutrients?.find { it.nutrientType == NutrientTypeDomainModel.PROTEIN }
        // (50 / 500) * 100 = 10
        assertEquals(10f, proteinsPer100g?.amount)
    }

    @Test
    fun `mapTo handles null weight and calories`() {
        val model = EdamamRecipeNetModel(
            uri = "recipe_id_123",
            totalWeight = null,
            calories = null
        )

        val result = mapper.mapTo(model)

        assertEquals(0f, result.weight)
        assertNull(result.caloriesTotal)
        assertNull(result.calories)
        assertNull(result.nutrients)
    }
}
