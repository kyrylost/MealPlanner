package dev.stukalo.mealplanner.data.network.edamam.food.source

/**
 * Network source for Edamam Food API.
 */
interface EdamamFoodNetSource {
    /**
     * Retrieves autocomplete hints for food items from Edamam.
     *
     * @param query The search query.
     * @param limit The maximum number of hints to return.
     * @return A list of matching food name suggestions.
     */
    suspend fun getAutoCompleteHints(query: String, limit: String): List<String>
}
