package dev.stukalo.mealplanner.presentation.feature.welcome.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.welcome_step_x_of_y
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.progress.circular.SemiCircularProgressBar
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun WelcomeHeader(currentStep: Int, totalSteps: Int, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
        modifier
            .statusBarsPadding()
            .padding(Theme.spacing.space32)
    ) {
        SemiCircularProgressBar(
            progress = currentStep.toFloat() / totalSteps.toFloat(),
            centerTitle = currentStep.toString(),
            centerSubtitle = stringResource(Res.string.welcome_step_x_of_y, currentStep, totalSteps),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun WelcomeHeaderPreview() {
    Theme {
        WelcomeHeader(
            currentStep = 1,
            totalSteps = 6,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
