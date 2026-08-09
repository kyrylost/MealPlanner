package dev.stukalo.mealplanner.presentation.core.ui.widget.quality

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.product_details_nova_label
import dev.stukalo.mealplanner.domain.model.food.quality.NovaGroup
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.styling.color.QualityColors
import org.jetbrains.compose.resources.stringResource

/**
 * A badge component that displays the NOVA food processing group of a product.
 *
 * The NOVA system classifies food products into four groups according to the extent
 * and purpose of industrial processing.
 *
 * @param group The [NovaGroup] value to display.
 * @param modifier The modifier to be applied to the badge.
 */
@Composable
fun NovaGroupBadge(group: NovaGroup, modifier: Modifier = Modifier) {
    val backgroundColor = when (group) {
        NovaGroup.GROUP_1 -> QualityColors.NovaGroup1
        NovaGroup.GROUP_2 -> QualityColors.NovaGroup2
        NovaGroup.GROUP_3 -> QualityColors.NovaGroup3
        NovaGroup.GROUP_4 -> QualityColors.NovaGroup4
    }

    val label = when (group) {
        NovaGroup.GROUP_1 -> "1"
        NovaGroup.GROUP_2 -> "2"
        NovaGroup.GROUP_3 -> "3"
        NovaGroup.GROUP_4 -> "4"
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
            text = stringResource(Res.string.product_details_nova_label, label),
            color = Theme.color.textOnPrimary,
            style = Theme.typography.bold12
        )
    }
}

@Preview
@Composable
private fun NovaGroupBadgePreview() {
    Theme {
        Row(
            modifier = Modifier.padding(Theme.spacing.space16),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space8)
        ) {
            NovaGroup.entries.forEach { group ->
                NovaGroupBadge(group = group)
            }
        }
    }
}
