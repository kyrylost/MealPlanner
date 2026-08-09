package dev.stukalo.mealplanner.presentation.feature.filters.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_max
import dev.stukalo.mealplanner.core.localization.common_min
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.input.RoundedPlaceholderTextField
import org.jetbrains.compose.resources.stringResource

@Composable
fun RangeInput(
    min: Int?,
    max: Int?,
    onMinChange: (Int?) -> Unit,
    onMaxChange: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space12)
    ) {
        RoundedPlaceholderTextField(
            value = min?.toString().orEmpty(),
            onValueChange = { onMinChange(it.toIntOrNull()) },
            placeholder = stringResource(Res.string.common_min),
            modifier = Modifier.weight(1f),
            backgroundColor = Theme.color.background.secondary.copy(alpha = 0.5f)
        )
        RoundedPlaceholderTextField(
            value = max?.toString().orEmpty(),
            onValueChange = { onMaxChange(it.toIntOrNull()) },
            placeholder = stringResource(Res.string.common_max),
            modifier = Modifier.weight(1f),
            backgroundColor = Theme.color.background.secondary.copy(alpha = 0.5f)
        )
    }
}
