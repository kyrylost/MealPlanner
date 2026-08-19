package dev.stukalo.mealplanner.presentation.feature.welcome.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.window.core.layout.WindowSizeClass
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.progress.AppLoader
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.AppSnackbarHost
import dev.stukalo.mealplanner.presentation.feature.welcome.component.WelcomeData
import dev.stukalo.mealplanner.presentation.feature.welcome.component.WelcomeHeader
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState

/**
 * The main UI content for the Welcome (Onboarding) flow.
 *
 * @param windowSizeClass The window size class for adaptive layout.
 * @param state The current view state.
 * @param snackbarHostState The snackbar host state.
 * @param onIntent Callback for processing user intents.
 */
@Composable
internal fun WelcomeContent(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    state: ViewState,
    snackbarHostState: SnackbarHostState,
    onIntent: (ViewIntent) -> Unit = {}
) {
    val divideTitleAndData =
        windowSizeClass
            .isWidthAtLeastBreakpoint(
                WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
            )

    Box(
        modifier =
        Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (divideTitleAndData) {
                Row(
                    modifier =
                    Modifier
                        .background(color = Theme.color.background.secondary)
                ) {
                    WelcomeHeader(
                        currentStep = state.currentStep,
                        totalSteps = 7,
                        modifier = Modifier.weight(1f).fillMaxHeight().zIndex(1f)
                    )
                    WelcomeData(
                        state = state,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
            } else {
                Column(
                    modifier =
                    Modifier
                        .background(color = Theme.color.background.secondary)
                ) {
                    WelcomeHeader(
                        currentStep = state.currentStep,
                        totalSteps = 7,
                        modifier = Modifier.fillMaxWidth()
                    )
                    WelcomeData(
                        state = state,
                        onIntent = onIntent,
                        modifier =
                        Modifier
                            .fillMaxHeight()
                            .clip(
                                RoundedCornerShape(
                                    topStart = Theme.radius.radius24,
                                    topEnd = Theme.radius.radius24
                                )
                            )
                    )
                }
            }
        }

        if (state.isLoading) {
            AppLoader()
        }

        AppSnackbarHost(
            hostState = snackbarHostState,
            modifier =
            Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
private fun WelcomeContentPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            WelcomeContent(
                state = ViewState(currentStep = 1),
                snackbarHostState = remember { SnackbarHostState() }
            )
        }
    }
}
