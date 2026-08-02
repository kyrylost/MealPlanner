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
import dev.stukalo.mealplanner.core.localization.welcome_gender_female
import dev.stukalo.mealplanner.core.localization.welcome_gender_label
import dev.stukalo.mealplanner.core.localization.welcome_gender_male
import dev.stukalo.mealplanner.domain.model.user.GenderDomainModel
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GenderSection(state: ViewState, onIntent: (ViewIntent) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.welcome_gender_label),
            style = Theme.typography.regular12,
            modifier = Modifier.padding(top = Theme.spacing.space24, bottom = Theme.spacing.space12)
        )

        GenderDomainModel.entries.forEach { gender ->
            val nameRes =
                when (gender) {
                    GenderDomainModel.MALE -> Res.string.welcome_gender_male
                    GenderDomainModel.FEMALE -> Res.string.welcome_gender_female
                }

            val isSelected = state.gender == gender
            Card(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = Theme.spacing.space4)
                    .clip(RoundedCornerShape(Theme.spacing.space16))
                    .clickable { onIntent(ViewIntent.OnChangeGenderInputIntent(gender)) },
                colors =
                CardDefaults.cardColors(
                    containerColor =
                    if (isSelected) {
                        Theme.color.primary
                    } else {
                        Theme.color.backgroundSecondary.copy(
                            alpha = 0.1f
                        )
                    }
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
private fun GenderSectionPreview() {
    Theme {
        GenderSection(
            state = ViewState(gender = GenderDomainModel.MALE),
            onIntent = {}
        )
    }
}
