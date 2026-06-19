package dev.stukalo.mealplanner.presentation.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationDirection {

    @Serializable
    data object Gateway: NavigationDirection

    @Serializable
    data object Welcome: NavigationDirection

    @Serializable
    data object Onboarding: NavigationDirection

    @Serializable
    data object BarcodeScanner: NavigationDirection

    @Serializable
    data object Home: NavigationDirection

    @Serializable
    data object Statistics: NavigationDirection

    @Serializable
    data object Search: NavigationDirection

    @Serializable
    data class RecipeDetails(
        val recipeId: String
    ): NavigationDirection

}
