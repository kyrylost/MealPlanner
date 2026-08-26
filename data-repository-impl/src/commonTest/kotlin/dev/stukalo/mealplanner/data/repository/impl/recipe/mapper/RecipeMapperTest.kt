package dev.stukalo.mealplanner.data.repository.impl.recipe.mapper

import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamIngredientNetModel
import dev.stukalo.mealplanner.data.network.edamam.recipe.model.EdamamRecipeNetModel
import kotlin.test.Test
import kotlin.test.assertEquals

class RecipeMapperTest {
    private val edamamMapper = EdamamRecipeToProductMapper()
    private val mapper = RecipeMapper(edamamMapper)

    @Test
    fun `mapTo correctly maps Edamam recipe to domain recipe`() {
        val model = EdamamRecipeNetModel(
            uri = "recipe_id_123",
            label = "Recipe Label",
            yield = 4.0,
            totalTime = 30.0,
            url = "http://recipe.url",
            ingredientLines = listOf("Line 1", "Line 2"),
            ingredients = listOf(
                EdamamIngredientNetModel(
                    food = "Food 1",
                    quantity = 1.0,
                    measure = "cup",
                    weight = 100.0,
                    foodId = "food1"
                )
            ),
            cuisineType = listOf("Italian"),
            dishType = listOf("Main Course"),
            healthLabels = listOf("Vegan"),
            instructionLines = listOf("Step 1", "Step 2")
        )

        val result = mapper.mapTo(model)

        assertEquals("123", result.id)
        assertEquals(4, result.servings)
        assertEquals(30, result.totalTime)
        assertEquals("http://recipe.url", result.url)
        assertEquals(2, result.ingredientLines?.size)
        assertEquals(1, result.ingredients?.size)
        assertEquals("Food 1", result.ingredients?.first()?.name)
        assertEquals(listOf("Italian"), result.cuisineType)
        assertEquals(listOf("Main Course"), result.dishType)
        assertEquals(listOf("Vegan"), result.healthLabels)
        assertEquals(listOf("Step 1", "Step 2"), result.instructionLines)
    }
}
