package dev.stukalo.mealplanner.presentation.feature.settings.screen.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_back
import dev.stukalo.mealplanner.core.localization.common_settings
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBack
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = Theme.spacing.space16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = IconBack,
                contentDescription = stringResource(Res.string.common_back),
                tint = Theme.color.textPrimary
            )
        }
        
        Spacer(modifier = Modifier.width(Theme.spacing.space8))
        
        Text(
            text = stringResource(Res.string.common_settings),
            style = Theme.typography.bold16,
            color = Theme.color.textPrimary
        )
    }
}
