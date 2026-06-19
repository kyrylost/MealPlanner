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
    data object MainFlow: NavigationDirection

    @Serializable
    data object Search: NavigationDirection

    @Serializable
    data class ProcessingFlow(
        val url: String
    ): NavigationDirection

}
