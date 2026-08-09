package dev.stukalo.mealplanner.presentation.feature.host

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.presentation.core.navigation.NavigationDirection
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.color.palette.ThemeColorPalette
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HostScreen() {
    val viewModel: HostViewModel = koinViewModel()

    val navController = rememberNavController()
    val themePalette by viewModel.themePalette.collectAsStateWithLifecycle()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isGateway = navBackStackEntry?.destination?.hasRoute<NavigationDirection.Gateway>() == true

    val themeColorPalette =
        when (themePalette) {
            ColorPaletteDomainModel.GREEN -> ThemeColorPalette.GREEN
            else -> ThemeColorPalette.ORANGE
        }

    Theme(
        palette = themeColorPalette,
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
