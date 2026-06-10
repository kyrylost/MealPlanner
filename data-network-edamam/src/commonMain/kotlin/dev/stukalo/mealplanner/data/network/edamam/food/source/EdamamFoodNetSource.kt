package dev.stukalo.mealplanner.data.network.edamam.food.source

interface EdamamFoodNetSource {
    suspend fun getAutoCompleteHints(
        query: String,
        limit: String,
    ): List<String>
}
