package dev.stukalo.mealplanner.presentation.core.ui.widget.quality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.product_details_nutriscore_label
import dev.stukalo.mealplanner.domain.model.food.quality.NutriScore
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.color.QualityColors
import org.jetbrains.compose.resources.stringResource

/**
 * A badge component that displays the Nutri-Score of a product.
 *
 * The Nutri-Score is a nutrition label that converts the nutritional value of products
 * into an overall score on a scale from A to E.
 *
 * @param score The [NutriScore] value to display.
 * @param modifier The modifier to be applied to the badge.
 */
@Composable
fun NutriScoreBadge(score: NutriScore, modifier: Modifier = Modifier) {
    val backgroundColor = when (score) {
        NutriScore.A -> QualityColors.NutriScoreA
        NutriScore.B -> QualityColors.NutriScoreB
        NutriScore.C -> QualityColors.NutriScoreC
        NutriScore.D -> QualityColors.NutriScoreD
        NutriScore.E -> QualityColors.NutriScoreE
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(Theme.radius.radius8)
            )
            .padding(horizontal = Theme.spacing.space8, vertical = Theme.spacing.space4)
    ) {
        Text(
            text = stringResource(Res.string.product_details_nutriscore_label, score.name),
            color = Theme.color.textOnPrimary,
            style = Theme.typography.bold12
        )
    }
}

@Preview
@Composable
private fun NutriScoreBadgePreview() {
    Theme {
        FlowRow(
            modifier = Modifier.padding(Theme.spacing.space16),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space8),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing.space8)
        ) {
            NutriScore.entries.forEach { score ->
                NutriScoreBadge(score = score)
            }
        }
    }
}
