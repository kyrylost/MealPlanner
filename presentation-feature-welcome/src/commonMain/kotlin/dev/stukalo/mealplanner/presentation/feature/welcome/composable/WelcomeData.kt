package dev.stukalo.mealplanner.presentation.feature.welcome.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.common_cancel
import dev.stukalo.mealplanner.core.localization.common_ok
import dev.stukalo.mealplanner.core.localization.welcome_back_button
import dev.stukalo.mealplanner.core.localization.welcome_continue_button
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.primary.PrimaryButton
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.text.TextButton
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.welcome.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WelcomeData(modifier: Modifier = Modifier, state: ViewState, onIntent: (ViewIntent) -> Unit) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier =
        modifier
            .background(color = Theme.color.background)
    ) {
        AnimatedContent(
            targetState = state.currentStep,
            transitionSpec = {
                if (targetState > initialState) {
                    (
                        slideInHorizontally(
                            animationSpec = tween(300)
                        ) { it } + fadeIn(animationSpec = tween(300))
                        ) togetherWith
                        (slideOutHorizontally(animationSpec = tween(300)) { -it } + fadeOut(animationSpec = tween(300)))
                } else {
                    (
                        slideInHorizontally(
                            animationSpec = tween(300)
                        ) { -it } + fadeIn(animationSpec = tween(300))
                        ) togetherWith
                        (slideOutHorizontally(animationSpec = tween(300)) { it } + fadeOut(animationSpec = tween(300)))
                }.using(SizeTransform(clip = true))
            },
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) { step ->
            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Theme.spacing.space24)
            ) {
                when (step) {
                    1 -> NameAndDateSection(state, onIntent)
                    2 -> WeightSection(state, onIntent)
                    3 -> HeightSection(state, onIntent)
                    4 -> GenderSection(state, onIntent)
                    5 -> ActivityLevelSection(state, onIntent)
                    6 -> DietSection(state, onIntent)
                }
            }
        }

        val backButtonWeight by animateFloatAsState(
            targetValue = if (state.currentStep > 1) 1f else 0f,
            label = "BackButtonWeight"
        )

        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(Theme.spacing.space24),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing.space12 * backButtonWeight)
        ) {
            if (backButtonWeight > 0f) {
                TextButton(
                    text = stringResource(Res.string.welcome_back_button),
                    onClick = { onIntent(ViewIntent.OnBackClickIntent) },
                    modifier =
                    Modifier
                        .weight(backButtonWeight)
                        .alpha(backButtonWeight)
                )
            }

            PrimaryButton(
                text = stringResource(Res.string.welcome_continue_button),
                onClick = {
                    onIntent(ViewIntent.OnContinueClickIntent)
                    focusManager.clearFocus()
                },
                modifier = Modifier.weight(1f)
            )
        }

        if (state.showDatePicker) {
            val datePickerState = rememberDatePickerState()

            DatePickerDialog(
                onDismissRequest = {
                    onIntent(ViewIntent.OnHideDatePickerIntent)
                    focusManager.clearFocus()
                },
                confirmButton = {
                    TextButton(
                        text = stringResource(Res.string.common_ok),
                        onClick = {
                            onIntent(ViewIntent.OnChangeDateInputIntent(datePickerState.selectedDateMillis))
                            onIntent(ViewIntent.OnHideDatePickerIntent)
                            focusManager.clearFocus()
                        }
                    )
                },
                dismissButton = {
                    TextButton(
                        text = stringResource(Res.string.common_cancel),
                        onClick = {
                            onIntent(ViewIntent.OnHideDatePickerIntent)
                            focusManager.clearFocus()
                        }
                    )
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
