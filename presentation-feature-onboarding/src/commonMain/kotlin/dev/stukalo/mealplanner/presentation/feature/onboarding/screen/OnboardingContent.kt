package dev.stukalo.mealplanner.presentation.feature.onboarding.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.onboarding_finish
import dev.stukalo.mealplanner.core.localization.onboarding_next
import dev.stukalo.mealplanner.core.localization.onboarding_skip
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_desc
import dev.stukalo.mealplanner.core.localization.onboarding_slide1_title
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.button.primary.PrimaryButton
import dev.stukalo.mealplanner.presentation.core.ui.component.button.text.TextButton
import dev.stukalo.mealplanner.presentation.feature.onboarding.component.OnboardingSlide
import dev.stukalo.mealplanner.presentation.feature.onboarding.core.model.OnboardingSlideModel
import dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.onboarding.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

/**
 * The main UI content for the Onboarding screen.
 *
 * @param state The current view state.
 * @param onIntent Callback for processing user intents.
 */
@Composable
internal fun OnboardingContent(state: ViewState, onIntent: (ViewIntent) -> Unit = {}) {
    val pagerState = rememberPagerState { state.slides.size }

    LaunchedEffect(state.currentSlideIndex) {
        if (pagerState.currentPage != state.currentSlideIndex) {
            pagerState.animateScrollToPage(state.currentSlideIndex)
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        onIntent(ViewIntent.OnSlideChange(pagerState.currentPage))
    }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(Theme.color.background.primary)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(Theme.spacing.space48)
                .padding(horizontal = Theme.spacing.space16),
            contentAlignment = Alignment.CenterEnd
        ) {
            if (state.currentSlideIndex < state.slides.size - 1) {
                TextButton(
                    text = stringResource(Res.string.onboarding_skip),
                    onClick = { onIntent(ViewIntent.OnSkipClick) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            OnboardingSlide(slide = state.slides[index])
        }

        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(Theme.spacing.space24),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space8),
                modifier = Modifier.padding(bottom = Theme.spacing.space24)
            ) {
                repeat(state.slides.size) { index ->
                    val color = if (index == state.currentSlideIndex) {
                        Theme.color.brand.primary
                    } else {
                        Theme.color.surface.variant
                    }
                    Box(
                        modifier =
                        Modifier
                            .size(Theme.spacing.space8)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }

            val isLastSlide = state.currentSlideIndex == state.slides.size - 1
            val buttonText = if (isLastSlide) {
                stringResource(Res.string.onboarding_finish)
            } else {
                stringResource(Res.string.onboarding_next)
            }

            PrimaryButton(
                text = buttonText,
                onClick = { onIntent(ViewIntent.OnNextClick) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingContentPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            OnboardingContent(
                state = ViewState(
                    slides = listOf(
                        OnboardingSlideModel(
                            title = Res.string.onboarding_slide1_title,
                            description = Res.string.onboarding_slide1_desc
                        )
                    )
                )
            )
        }
    }
}
