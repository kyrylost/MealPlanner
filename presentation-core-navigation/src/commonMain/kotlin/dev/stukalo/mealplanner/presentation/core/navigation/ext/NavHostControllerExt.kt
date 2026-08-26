package dev.stukalo.mealplanner.presentation.core.navigation.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import dev.stukalo.mealplanner.core.common.util.AppLogger
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

/**
 * Extension for [SavedStateHandle] to set a [kotlinx.serialization.Serializable] object as a JSON string.
 */
inline fun <reified T : Any> SavedStateHandle.setSerializable(key: String, value: T?) {
    this[key] = value?.let { Json.encodeToString(it) }
}

/**
 * Extension for [SavedStateHandle] to get a [kotlinx.serialization.Serializable] object from a JSON string.
 */
inline fun <reified T : Any> SavedStateHandle.getSerializable(key: String): T? =
    this.get<String>(key)?.let { Json.decodeFromString<T>(it) }

/**
 * Extension for [SavedStateHandle] to get a [State] of a [kotlinx.serialization.Serializable] object.
 * This handles the JSON decoding internally.
 */
@Composable
inline fun <reified T : Any> SavedStateHandle.getSerializableState(key: String, initialValue: T? = null): State<T?> =
    this.getStateFlow<String?>(key, null)
        .map { it?.let { Json.decodeFromString<T>(it) } ?: initialValue }
        .collectAsState(initialValue)

/**
 * Safely navigates to a given route, catching any exceptions that occur during navigation.
 *
 * @param T The type of the route.
 * @param route The route to navigate to.
 * @param navOptions Optional navigation options.
 * @param navigatorExtras Optional navigator extras.
 * @param cleanBackStack Optional parameter to clean the backstack.
 */
fun <T : Any> NavHostController.safeNavigation(
    route: T,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
    cleanBackStack: Boolean = false
) {
    try {
        if (cleanBackStack) {
            this.popBackStack()
        }
        this.navigate(route, navOptions, navigatorExtras)
    } catch (ex: Exception) {
        AppLogger.e("Navigation", "Navigation error: ${ex.message}", ex)
    }
}

/**
 * Safely navigates to a given route using a [NavOptionsBuilder], catching any exceptions that occur during navigation.
 *
 * @param T The type of the route.
 * @param route The route to navigate to.
 * @param builder A lambda function to build [NavOptions].
 */
fun <T : Any> NavHostController.safeNavigation(route: T, builder: NavOptionsBuilder.() -> Unit) {
    try {
        this.navigate(route, builder)
    } catch (ex: Exception) {
        AppLogger.e("Navigation", "Navigation error: ${ex.message}", ex)
    }
}
