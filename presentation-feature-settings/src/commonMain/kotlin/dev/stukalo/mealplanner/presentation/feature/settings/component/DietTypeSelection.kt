package dev.stukalo.mealplanner.presentation.feature.settings.component

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
import org.jetbrains.compose.resources.stringResource

/**
 * A component for selecting the user's diet type.
 *
 * @param selectedDiet The currently selected diet type.
 * @param onDietSelected Callback when a new diet is selected.
 * @param modifier The modifier to apply to the component.
 */
@Composable
fun DietTypeSelection(
    selectedDiet: DietDomainModel?,
    onDietSelected: (DietDomainModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.welcome_diet_label),
            style = Theme.typography.regular12,
            modifier = Modifier.padding(bottom = Theme.spacing.space12)
        )

        DietDomainModel.entries.forEach { diet ->
            val nameRes = when (diet) {
                DietDomainModel.BALANCED_DIET -> Res.string.welcome_diet_balanced
                DietDomainModel.WEIGHT_GAIN -> Res.string.welcome_diet_weight_gain
                DietDomainModel.WEIGHT_LOSS -> Res.string.welcome_diet_weight_loss
                DietDomainModel.CUTTING_DIET -> Res.string.welcome_diet_cutting
            }

            val isSelected = selectedDiet == diet
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Theme.spacing.space4)
                    .clip(RoundedCornerShape(Theme.spacing.space16))
                    .clickable { onDietSelected(diet) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) {
                        Theme.color.brand.primary
                    } else {
                        Theme.color.background.secondary.copy(alpha = 0.5f)
                    }
                )
            ) {
                Box(modifier = Modifier.padding(Theme.spacing.space16), contentAlignment = Alignment.CenterStart) {
                    Text(
                        text = stringResource(nameRes),
                        style = Theme.typography.regular14,
                        color = if (isSelected) Theme.color.text.onPrimary else Theme.color.text.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DietTypeSelectionPreview() {
    Theme {
        DietTypeSelection(
            selectedDiet = DietDomainModel.BALANCED_DIET,
            onDietSelected = {}
        )
    }
}
