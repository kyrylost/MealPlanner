package dev.stukalo.mealplanner.presentation.core.navigation

import kotlinx.serialization.Serializable

/**
 * Defines all possible navigation destinations in the application.
 */
@Serializable
sealed interface NavigationDirection {
    @Serializable
    data object Gateway : NavigationDirection

    @Serializable
    data object Welcome : NavigationDirection

    @Serializable
    data object Onboarding : NavigationDirection

    @Serializable
    data object BarcodeScanner : NavigationDirection

    @Serializable
    data object Home : NavigationDirection

    @Serializable
    data object Statistics : NavigationDirection

    @Serializable
    data object ProductSearch : NavigationDirection

    @Serializable
    data object RecipeSearch : NavigationDirection

    @Serializable
    data object Settings : NavigationDirection

    @Serializable
    data object MealSchedule : NavigationDirection

    @Serializable
    data object Filters : NavigationDirection

    @Serializable
    data class RecipeDetails(val recipeId: String) : NavigationDirection

    @Serializable
    data class ProductDetails(val productId: String? = null, val barcode: String? = null) : NavigationDirection
}
