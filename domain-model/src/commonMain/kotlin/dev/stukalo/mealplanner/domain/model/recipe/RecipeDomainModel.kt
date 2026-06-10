package dev.stukalo.mealplanner.domain.model.recipe

import dev.stukalo.mealplanner.domain.model.food.ProductDomainModel

/**
 * Represents a complete recipe in the domain layer.
 *
 * This model encapsulates both the nutritional data (via [product]) and
 * culinary details required to display and prepare a meal.
 *
 * @property product The nutritional representation of the recipe, including images,
 * calories, and nutrient breakdown (both per 100g and total).

 * @property servings The number of servings this recipe yields.

 * @property totalTime The total time required to prepare and cook the recipe in minutes.

 * @property ingredients The structured list of ingredients used in the recipe.

 * @property ingredientLines The raw list of ingredient strings as provided by the source.

 * @property url The URL to the original recipe source.

 * @property cuisineType The type of cuisine.
 * * Available values:
 * American, Asian, British, Caribbean, Central Europe, Chinese, Eastern Europe, French, Greek,
 * Indian, Italian, Japanese, Korean, Kosher, Mediterranean, Mexican, Middle Eastern, Nordic,
 * South American, South East Asian.

 * @property dishType The type of dish.
 * * Available values:
 * Biscuits and cookies, Bread, Cereals, Condiments and sauces, Desserts, Drinks, Main course,
 * Pancake, Preps, Preserve, Salad, Sandwiches, Side dish, Soup, Starter, Sweets.

 * @property healthLabels Labels indicating health-related attributes.
 * * Available values:
 * alcohol-cocktail, alcohol-free, celery-free, crustacean-free, dairy-free, DASH, egg-free,
 * fish-free, fodmap-free, gluten-free, immuno-supportive, keto-friendly, kidney-friendly,
 * kosher, low-fat-abs, low-potassium, low-sugar, lupine-free, Mediterranean, mollusk-free,
 * mustard-free, no-oil-added, paleo, peanut-free, pescatarian, pork-free, red-meat-free,
 * sesame-free, shellfish-free, soy-free, sugar-conscious, sulfite-free, tree-nut-free,
 * vegan, vegetarian, wheat-free.
 */
data class RecipeDomainModel(
    val product: ProductDomainModel,
    val servings: Int? = null,
    val totalTime: Int? = null,
    val ingredients: List<IngredientDomainModel>? = null,
    val ingredientLines: List<String>? = null,
    val url: String? = null,
    val cuisineType: List<String>? = null,
    val dishType: List<String>? = null,
    val healthLabels: List<String>? = null,
)
