package dev.stukalo.mealplanner.presentation.feature.welcome.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.welcome_diet_balanced
import dev.stukalo.mealplanner.core.localization.welcome_diet_cutting
import dev.stukalo.mealplanner.core.localization.welcome_diet_label
import dev.stukalo.mealplanner.core.localization.welcome_diet_weight_gain
import dev.stukalo.mealplanner.core.localization.welcome_diet_weight_loss
import dev.stukalo.mealplanner.domain.model.user.DietDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DietSection(
    state: ViewState,
    onIntent: (ViewIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.welcome_diet_label),
            style = Theme.typography.regular12,
            modifier = Modifier.padding(top = Theme.spacing.space24, bottom = Theme.spacing.space12)
        )

        DietDomainModel.entries.forEach { diet ->
            val nameRes = when (diet) {
                DietDomainModel.BALANCED_DIET -> Res.string.welcome_diet_balanced
                DietDomainModel.WEIGHT_GAIN -> Res.string.welcome_diet_weight_gain
                DietDomainModel.WEIGHT_LOSS -> Res.string.welcome_diet_weight_loss
                DietDomainModel.CUTTING_DIET -> Res.string.welcome_diet_cutting
            }

            val isSelected = state.diet == diet
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Theme.spacing.space4)
                    .clip(RoundedCornerShape(Theme.spacing.space16))
                    .clickable { onIntent(ViewIntent.OnChangeDietInputIntent(diet)) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Theme.color.primary else Theme.color.backgroundSecondary.copy(alpha = 0.1f)
                )
            ) {
                Box(modifier = Modifier.padding(Theme.spacing.space16), contentAlignment = Alignment.CenterStart) {
                    Text(
                        text = stringResource(nameRes),
                        style = Theme.typography.regular14,
                        color = if (isSelected) Theme.color.textOnPrimary else Theme.color.textPrimary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DietSectionPreview() {
    Theme {
        DietSection(
            state = ViewState(diet = DietDomainModel.BALANCED_DIET),
            onIntent = {}
        )
    }
}
