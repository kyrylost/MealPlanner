package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.settings_meal_config
import dev.stukalo.mealplanner.core.localization.settings_theme_choice
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.feature.settings.screen.component.SettingsHeader
import dev.stukalo.mealplanner.presentation.feature.settings.screen.component.ThemeOption
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SettingsContent(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background)
            .statusBarsPadding()
    ) {
        SettingsHeader(onBackClick = { onIntent(ViewIntent.OnBackClick) })

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(Theme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
        ) {
            item {
                Text(
                    text = stringResource(Res.string.settings_meal_config),
                    style = Theme.typography.bold14,
                    color = Theme.color.textPrimary
                )
            }
            
            item {
                Text(
                    text = stringResource(Res.string.settings_theme_choice),
                    style = Theme.typography.bold14,
                    color = Theme.color.textPrimary
                )
            }
            
            items(ColorPaletteDomainModel.entries) { palette ->
                ThemeOption(
                    palette = palette,
                    onClick = {
                        onIntent(ViewIntent.OnThemeClick(palette))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsContentPreview() {
    Theme {
        SettingsContent(
            state = ViewState(),
            onIntent = {}
        )
    }
}
