package dev.stukalo.mealplanner.presentation.feature.statistics.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.statistics_streak
import dev.stukalo.mealplanner.core.localization.statistics_streak_days
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconFire
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun StreakCard(streak: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = Theme.shape.normalRoundedCornerShape,
        color = Theme.color.background.secondary
    ) {
        Row(
            modifier = Modifier
                .padding(Theme.spacing.space16)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(Res.string.statistics_streak),
                    style = Theme.typography.regular14,
                    color = Theme.color.text.secondary
                )
                Text(
                    text = stringResource(Res.string.statistics_streak_days, streak),
                    style = Theme.typography.bold36,
                    color = Theme.color.brand.primary
                )
            }
            Icon(
                imageVector = IconFire,
                contentDescription = null,
                tint = Theme.color.brand.primary,
                modifier = Modifier.size(Theme.spacing.space48)
            )
        }
    }
}

@Preview
@Composable
private fun StreakCardPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            StreakCard(
                streak = 7,
                modifier = Modifier.padding(Theme.spacing.space16)
            )
        }
    }
}
