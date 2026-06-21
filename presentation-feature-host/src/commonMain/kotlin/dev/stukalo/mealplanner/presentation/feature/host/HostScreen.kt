package dev.stukalo.mealplanner.presentation.feature.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.color.ThemeColorPalette
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HostScreen(
    viewModel: HostViewModel = koinViewModel()
) {
    val themePalette by viewModel.themePalette.collectAsStateWithLifecycle()

    val themeColorPalette = when (themePalette) {
        ColorPaletteDomainModel.ORANGE -> ThemeColorPalette.ORANGE
        ColorPaletteDomainModel.GREEN -> ThemeColorPalette.GREEN
    }

    Theme(palette = themeColorPalette) {
        AppNavHost()
    }
}
