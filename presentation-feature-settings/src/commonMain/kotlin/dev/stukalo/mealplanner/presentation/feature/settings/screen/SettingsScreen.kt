package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.domain.model.setting.ColorPaletteDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.color.ThemeColorPalette
import dev.stukalo.mealplanner.presentation.core.styling.color.toPrimaryColor
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBack
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
) {
    
    val viewModel: SettingsViewModel = koinViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.background)
            .statusBarsPadding()
    ) {
        SettingsHeader(onBackClick = onBackClick)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(Theme.spacing.space16),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
        ) {
            item {
                Text(
                    text = "Theme Choice",
                    style = Theme.typography.bold14,
                    color = Theme.color.textPrimary
                )
            }
            
            items(ColorPaletteDomainModel.entries) { palette ->
                ThemeOption(
                    palette = palette,
                    onClick = {
                        viewModel.setTheme(palette)
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingsHeader(
    onBackClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = Theme.spacing.space8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = IconBack,
                contentDescription = "Back",
                tint = Theme.color.textPrimary
            )
        }
        
        Spacer(modifier = Modifier.width(Theme.spacing.space8))
        
        Text(
            text = "Settings",
            style = Theme.typography.bold16,
            color = Theme.color.textPrimary
        )
    }
}

@Composable
fun ThemeOption(
    palette: ColorPaletteDomainModel,
    onClick: () -> Unit
) {
    val themeColorPalette = when (palette) {
        ColorPaletteDomainModel.ORANGE -> ThemeColorPalette.ORANGE
        ColorPaletteDomainModel.GREEN -> ThemeColorPalette.GREEN
    }
    val primary = themeColorPalette.toPrimaryColor()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Theme.shape.normalRoundedCornerShape)
            .background(Theme.color.backgroundSecondary)
            .clickable(onClick = onClick)
            .padding(Theme.spacing.space16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space16)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(primary)
        )
        Text(
            text = palette.name.lowercase().replaceFirstChar { it.uppercase() },
            style = Theme.typography.regular14,
            color = Theme.color.textPrimary
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    Theme {
        SettingsScreen(onBackClick = {})
    }
}
