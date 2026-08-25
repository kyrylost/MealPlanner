package dev.stukalo.mealplanner.presentation.feature.settings.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LifecycleResumeEffect
import dev.stukalo.mealplanner.presentation.core.ui.base.mvi.MviScreen
import dev.stukalo.mealplanner.presentation.core.ui.component.permission.HealthPermissionGate
import dev.stukalo.mealplanner.presentation.core.ui.component.permission.NotificationPermissionGate
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewEvent
import dev.stukalo.mealplanner.presentation.feature.settings.screen.contract.ViewIntent
import org.koin.compose.viewmodel.koinViewModel

/**
 * The entry point for the Settings feature.
 * Handles ViewModel lifecycle, health permission requests, and event navigation.
 */
@Composable
internal fun SettingsScreen() {
    val viewModel: SettingsViewModel = koinViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    LifecycleResumeEffect(Unit) {
        viewModel.onIntent(ViewIntent.OnResume)
        onPauseOrDispose {}
    }

    MviScreen(
        viewModel = viewModel,
        onSingleEvent = { event ->
            when (event) {
                ViewEvent.NavigateBack -> { /* No back in tab */ }
            }
        },
        snackbarHostState = snackbarHostState
    ) { state ->
        HealthPermissionGate(
            permissions = state.healthPermissionsToRequest,
            trigger = state.shouldRequestHealthPermissions,
            onPermissionsGranted = { isGranted ->
                viewModel.onIntent(ViewIntent.OnHealthPermissionsResult(isGranted))
            },
            onTriggerReset = {
                viewModel.onIntent(ViewIntent.OnHealthPermissionsHandled)
            },
            onRequestPermissions = {
                viewModel.onIntent(ViewIntent.OnRequestHealthPermissions)
            }
        )

        NotificationPermissionGate(
            trigger = state.shouldRequestNotificationPermission,
            onPermissionResult = { isGranted ->
                viewModel.onIntent(ViewIntent.OnNotificationPermissionResult(isGranted))
            },
            onTriggerReset = {
                viewModel.onIntent(ViewIntent.OnNotificationPermissionHandled)
            }
        )

        SettingsContent(
            state = state,
            onIntent = viewModel::onIntent
        )
    }
}
