package dev.stukalo.mealplanner.presentation.feature.host

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.domain.model.setting.ThemeModeDomainModel
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.platform.setLocale
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.color.palette.ThemeColorPalette
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import org.koin.compose.viewmodel.koinViewModel

/**
 * The root screen of the application.
 * Responsibility: Theme management, Locale management, Navigation root.
 */
@Composable
fun HostScreen() {
    val viewModel: HostViewModel = koinViewModel()
    val navController = rememberNavController()

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = {},
        content = { state ->
            LaunchedEffect(state.locale) {
                setLocale(state.locale)
            }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val isGateway = navBackStackEntry?.destination?.hasRoute<NavigationDirection.Gateway>() == true

            val palette =
                when (state.colorPalette) {
                    ColorPaletteDomainModel.GREEN -> ThemeColorPalette.GREEN
                    ColorPaletteDomainModel.LIME -> ThemeColorPalette.LIME
                    ColorPaletteDomainModel.PINK -> ThemeColorPalette.PINK
                    else -> ThemeColorPalette.ORANGE
                }

            val darkTheme = when (state.themeMode) {
                ThemeModeDomainModel.AUTO -> isSystemInDarkTheme()
                ThemeModeDomainModel.LIGHT -> false
                ThemeModeDomainModel.DARK -> true
            }

            key(state.locale) {
                Theme(
                    darkTheme = darkTheme,
                    palette = palette,
                    animatePaletteChange = !isGateway
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Theme.color.background.primary
                    ) {
                        AppNavHost(navController = navController)
                    }
                }
            }
        }
    )
}
