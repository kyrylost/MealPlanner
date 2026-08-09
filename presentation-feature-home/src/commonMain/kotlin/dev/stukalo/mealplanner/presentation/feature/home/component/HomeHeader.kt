package dev.stukalo.mealplanner.presentation.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.home_hello
import dev.stukalo.mealplanner.core.localization.home_hello_name
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomeHeader(userName: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column {
            Text(
                text = stringResource(Res.string.home_hello),
                style = Theme.typography.handwrittenRegular64,
                color = Theme.color.text.secondary
            )
            Text(
                text = stringResource(Res.string.home_hello_name, userName),
                style = Theme.typography.handwrittenSemibold64,
                color = Theme.color.text.primary
            )
        }
    }
}
