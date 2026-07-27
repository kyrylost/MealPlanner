package dev.stukalo.mealplanner.presentation.feature.filters.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_max
import dev.stukalo.mealplanner.core.localization.common_min
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = min?.toString() ?: "",
            onValueChange = { onMinChange(it.toIntOrNull()) },
            label = { Text(stringResource(Res.string.common_min)) },
            modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = max?.toString() ?: "",
            onValueChange = { onMaxChange(it.toIntOrNull()) },
            label = { Text(stringResource(Res.string.common_max)) },
            modifier = Modifier.weight(1f)
        )
    }
}
