package dev.stukalo.mealplanner.presentation.feature.welcome.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.welcome_title
import dev.stukalo.mealplanner.core.localization.welcome_subtitle
import org.jetbrains.compose.resources.stringResource

@Preview
@Composable
fun WelcomeTitle(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .statusBarsPadding()
            .padding(Theme.spacing.space48)
    ) {
        Text(
            text = stringResource(Res.string.welcome_title),
            style = Theme.typography.titleLarge,
            color = Theme.color.iconWhite
        )
        Text(
            text = stringResource(Res.string.welcome_subtitle),
            textAlign = TextAlign.Center,
            style = Theme.typography.titleNormal,
            color = Theme.color.iconWhite,
            modifier = Modifier.padding(top = Theme.spacing.space12)
        )
    }
}