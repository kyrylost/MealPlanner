package dev.stukalo.mealplanner.domain.model.recipe

/**
 * Represents a single ingredient in a recipe.
 *
 * @property name The name of the ingredient (e.g., "chicken breast").
 * @property quantity The numeric amount of the ingredient.
 * @property measure The unit of measurement (e.g., "cup", "gram", "unit").
 * @property weight The weight of the ingredient in grams.
 * @property imageUrl An optional URL for the ingredient's image.
 * @property category The food category this ingredient belongs to (e.g., "poultry").
 */
data class IngredientDomainModel(
    val name: String? = null,
    val quantity: Float? = null,
    val measure: String? = null,
    val weight: Float? = null,
    val imageUrl: String? = null,
    val category: String? = null
)
