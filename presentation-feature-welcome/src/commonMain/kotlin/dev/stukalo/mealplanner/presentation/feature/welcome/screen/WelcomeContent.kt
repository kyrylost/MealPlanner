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
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.window.core.layout.WindowSizeClass
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.snackbar.AppSnackbarHost
import dev.stukalo.mealplanner.presentation.feature.welcome.composable.WelcomeData
import dev.stukalo.mealplanner.presentation.feature.welcome.composable.WelcomeTitle
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState

@Composable
internal fun WelcomeContent(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    state: ViewState,
    snackbarHostState: SnackbarHostState,
    onIntent: (ViewIntent) -> Unit = {},
) {
    val divideTitleAndData =
        windowSizeClass
            .isWidthAtLeastBreakpoint(
                WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND
            )

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (divideTitleAndData) {
                Row(
                    modifier = Modifier
                        .background(color = Theme.color.backgroundSecondary)
                ) {
                    WelcomeTitle(
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                    WelcomeData(
                        state = state,
                        onIntent = onIntent,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .background(color = Theme.color.darkGray)
                ) {
                    WelcomeTitle(
                        modifier = Modifier.fillMaxWidth()
                    )
                    WelcomeData(
                        state = state,
                        onIntent = onIntent,
                        modifier = Modifier
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

        AppSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter)
        )
    }
}
