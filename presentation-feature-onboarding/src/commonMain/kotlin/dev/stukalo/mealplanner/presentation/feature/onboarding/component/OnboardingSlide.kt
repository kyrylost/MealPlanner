package dev.stukalo.mealplanner.presentation.feature.onboarding.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.window.core.layout.WindowSizeClass
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_desc
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_title
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.feature.onboarding.core.model.OnboardingSlideModel

private const val ASPECT_RATIO_SQUARE = 1f

@Composable
internal fun OnboardingSlide(
    slide: OnboardingSlideModel,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    val isWideScreen =
        windowSizeClass.isWidthAtLeastBreakpoint(
            WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
        )

    if (isWideScreen) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space48),
            modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = Theme.spacing.space64)
        ) {
            SlideImage(
                slide = slide,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
            SlideContent(
                slide = slide,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                horizontalAlignment = Alignment.Start
            )
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = Theme.spacing.space24)
        ) {
            SlideImage(
                slide = slide,
                modifier = Modifier.fillMaxWidth().aspectRatio(ASPECT_RATIO_SQUARE)
            )

            Spacer(modifier = Modifier.height(Theme.spacing.space32))

            SlideContent(
                slide = slide,
                textAlign = TextAlign.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingSlidePreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            OnboardingSlide(
                slide = OnboardingSlideModel(
                    title = Res.string.onboarding_slide1_title,
                    description = Res.string.onboarding_slide1_desc
                ),
                windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            )
        }
    }
}
