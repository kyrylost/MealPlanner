package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.LifecycleResumeEffect
import dev.stukalo.mealplanner.core.platform.HealthManager
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.core.ui.widget.permission.HealthPermissionGate
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewIntent
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

/**
 * The entry point for the Settings feature.
 * Handles ViewModel lifecycle, health permission requests, and event navigation.
 */
@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = koinViewModel()
    val healthManager: HealthManager = koinInject()

    LifecycleResumeEffect(Unit) {
        viewModel.onIntent(ViewIntent.OnResume)
        onPauseOrDispose {}
    }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> { /* No back in tab */ }
                ViewEvent.OpenHealthSettings -> {
                    healthManager.openHealthSettings()
                }
                ViewEvent.InstallHealthConnect -> {
                    healthManager.installHealthConnect()
                }
            }
        }
    ) { state ->
        HealthPermissionGate(
            permissions = state.healthPermissionsToRequest,
            trigger = state.shouldRequestHealthPermissions,
            onPermissionsGranted = { isGranted ->
                viewModel.onIntent(ViewIntent.OnHealthPermissionsResult(isGranted))
            },
            onTriggerReset = {
                viewModel.onIntent(ViewIntent.OnHealthPermissionsHandled)
            }
        )

        SettingsContent(
            state = state,
            onIntent = viewModel::onIntent
        )
    }
}
