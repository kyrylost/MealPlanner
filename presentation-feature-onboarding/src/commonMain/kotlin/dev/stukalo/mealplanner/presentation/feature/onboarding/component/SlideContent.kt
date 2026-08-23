package dev.stukalo.mealplanner.presentation.feature.onboarding.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_desc
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_title
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.feature.onboarding.core.model.OnboardingSlideModel
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SlideContent(
    slide: OnboardingSlideModel,
    modifier: Modifier = Modifier,
    textAlign: TextAlign,
    horizontalAlignment: Alignment.Horizontal
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = stringResource(slide.title),
            style = Theme.typography.bold36,
            color = Theme.color.text.primary,
            textAlign = textAlign
        )

        Spacer(modifier = Modifier.height(Theme.spacing.space16))

        Text(
            text = stringResource(slide.description),
            style = Theme.typography.regular14,
            color = Theme.color.text.secondary,
            textAlign = textAlign
        )
    }
}

@Preview
@Composable
private fun SlideContentPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            SlideContent(
                slide = OnboardingSlideModel(
                    title = Res.string.onboarding_slide1_title,
                    description = Res.string.onboarding_slide1_desc
                ),
                textAlign = TextAlign.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(Theme.spacing.space24)
            )
        }
    }
}
