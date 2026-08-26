package dev.stukalo.mealplanner.presentation.feature.productdetails.component.quality

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
import org.jetbrains.compose.resources.stringResource

/**
 * A badge component that displays the NOVA food processing group of a product.
 *
 * @param group The [NovaGroup] value to display.
 * @param modifier The modifier to be applied to the badge.
 */
@Composable
fun NovaGroupBadge(group: NovaGroup, modifier: Modifier = Modifier) {
    val backgroundColor = when (group) {
        NovaGroup.GROUP_1 -> Theme.color.quality.novaGroup1
        NovaGroup.GROUP_2 -> Theme.color.quality.novaGroup2
        NovaGroup.GROUP_3 -> Theme.color.quality.novaGroup3
        NovaGroup.GROUP_4 -> Theme.color.quality.novaGroup4
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
            color = Theme.color.text.onPrimary,
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
