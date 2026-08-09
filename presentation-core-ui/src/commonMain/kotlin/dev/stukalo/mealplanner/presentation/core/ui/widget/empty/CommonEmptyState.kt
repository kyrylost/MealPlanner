package dev.stukalo.mealplanner.presentation.core.ui.widget.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_clear_filters
import dev.stukalo.mealplanner.core.localization.common_no_results
import dev.stukalo.mealplanner.core.localization.common_no_results_desc
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconSearch
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.PrimaryButton
import org.jetbrains.compose.resources.stringResource

/**
 * A generic empty state component used across the app.
 *
 * @param title The main text to display.
 * @param modifier The modifier for the component.
 * @param description An optional secondary text to provide more context.
 * @param icon An optional icon to display above the title.
 * @param actionText The text for the optional action button.
 * @param onActionClick The callback for the optional action button.
 */
@Composable
fun CommonEmptyState(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: ImageVector? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Theme.spacing.space32),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(Theme.spacing.space64),
                tint = Theme.color.iconPrimary
            )
            Spacer(modifier = Modifier.height(Theme.spacing.space24))
        }

        Text(
            text = title,
            style = Theme.typography.bold16,
            color = Theme.color.textPrimary,
            textAlign = TextAlign.Center
        )

        if (description != null) {
            Spacer(modifier = Modifier.height(Theme.spacing.space8))
            Text(
                text = description,
                style = Theme.typography.regular14,
                color = Theme.color.textSecondary,
                textAlign = TextAlign.Center
            )
        }

        if (actionText != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(Theme.spacing.space24))
            PrimaryButton(
                text = actionText,
                onClick = onActionClick
            )
        }
    }
}

@Preview
@Composable
private fun CommonEmptyStatePreview() {
    Theme {
        CommonEmptyState(
            title = stringResource(Res.string.common_no_results),
            description = stringResource(Res.string.common_no_results_desc),
            icon = IconSearch,
            actionText = stringResource(Res.string.common_clear_filters),
            onActionClick = {}
        )
    }
}
