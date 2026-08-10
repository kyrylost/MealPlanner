package dev.stukalo.mealplanner.presentation.feature.settings.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.welcome_activity_high_desc
import dev.stukalo.mealplanner.core.localization.welcome_activity_high_name
import dev.stukalo.mealplanner.core.localization.welcome_activity_label
import dev.stukalo.mealplanner.core.localization.welcome_activity_low_desc
import dev.stukalo.mealplanner.core.localization.welcome_activity_low_name
import dev.stukalo.mealplanner.core.localization.welcome_activity_medium_desc
import dev.stukalo.mealplanner.core.localization.welcome_activity_medium_name
import dev.stukalo.mealplanner.core.localization.welcome_activity_very_high_desc
import dev.stukalo.mealplanner.core.localization.welcome_activity_very_high_name
import dev.stukalo.mealplanner.core.localization.welcome_activity_very_low_desc
import dev.stukalo.mealplanner.core.localization.welcome_activity_very_low_name
import dev.stukalo.mealplanner.domain.model.user.ActivityLevelDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import org.jetbrains.compose.resources.stringResource

/**
 * A component for selecting the user's physical activity level.
 *
 * @param selectedLevel The currently selected activity level.
 * @param onLevelSelected Callback when a new level is selected.
 * @param modifier The modifier to apply to the component.
 */
@Composable
fun ActivityLevelSelection(
    selectedLevel: ActivityLevelDomainModel?,
    onLevelSelected: (ActivityLevelDomainModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.welcome_activity_label),
            style = Theme.typography.regular12,
            modifier = Modifier.padding(bottom = Theme.spacing.space12)
        )

        ActivityLevelDomainModel.entries.forEach { level ->
            val nameRes = when (level) {
                ActivityLevelDomainModel.VERY_LOW -> Res.string.welcome_activity_very_low_name
                ActivityLevelDomainModel.LOW -> Res.string.welcome_activity_low_name
                ActivityLevelDomainModel.MEDIUM -> Res.string.welcome_activity_medium_name
                ActivityLevelDomainModel.HIGH -> Res.string.welcome_activity_high_name
                ActivityLevelDomainModel.VERY_HIGH -> Res.string.welcome_activity_very_high_name
            }
            val descRes = when (level) {
                ActivityLevelDomainModel.VERY_LOW -> Res.string.welcome_activity_very_low_desc
                ActivityLevelDomainModel.LOW -> Res.string.welcome_activity_low_desc
                ActivityLevelDomainModel.MEDIUM -> Res.string.welcome_activity_medium_desc
                ActivityLevelDomainModel.HIGH -> Res.string.welcome_activity_high_desc
                ActivityLevelDomainModel.VERY_HIGH -> Res.string.welcome_activity_very_high_desc
            }

            val isSelected = selectedLevel == level
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Theme.spacing.space4)
                    .clip(RoundedCornerShape(Theme.spacing.space16))
                    .clickable { onLevelSelected(level) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) {
                        Theme.color.brand.primary
                    } else {
                        Theme.color.background.secondary.copy(alpha = 0.5f)
                    }
                )
            ) {
                Column(modifier = Modifier.padding(Theme.spacing.space16)) {
                    Text(
                        text = stringResource(nameRes),
                        style = Theme.typography.regular14,
                        color = if (isSelected) Theme.color.text.onPrimary else Theme.color.text.primary
                    )
                    Text(
                        text = stringResource(descRes),
                        style = Theme.typography.regular12,
                        color = if (isSelected) Theme.color.text.onPrimaryVariant else Theme.color.text.secondary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ActivityLevelSelectionPreview() {
    Theme {
        ActivityLevelSelection(
            selectedLevel = ActivityLevelDomainModel.MEDIUM,
            onLevelSelected = {}
        )
    }
}
