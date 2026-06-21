package dev.stukalo.mealplanner.presentation.feature.statistics.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconSettings

@Composable
fun StatisticsScreen(
    onSettingsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.color.background)
    ) {
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(Theme.spacing.space16)
        ) {
            Icon(
                imageVector = IconSettings,
                contentDescription = "Settings",
                tint = Theme.color.iconPrimary
            )
        }

        Text(
            text = "Statistics coming soon",
            style = Theme.typography.bold16,
            color = Theme.color.textPrimary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
