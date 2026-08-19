package dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.stukalo.mealplanner.core.localization.Res
import dev.stukalo.mealplanner.core.localization.barcode_scanner_enter_barcode
import dev.stukalo.mealplanner.core.localization.barcode_scanner_manual_entry
import dev.stukalo.mealplanner.core.localization.barcode_scanner_title
import dev.stukalo.mealplanner.core.localization.common_search
import dev.stukalo.mealplanner.presentation.core.styling.Theme
import dev.stukalo.mealplanner.presentation.core.ui.component.button.primary.PrimaryButton
import dev.stukalo.mealplanner.presentation.core.ui.component.header.CommonHeader
import dev.stukalo.mealplanner.presentation.core.ui.component.input.RoundedPlaceholderTextField
import dev.stukalo.mealplanner.presentation.core.ui.component.snackbar.AppSnackbarHost
import dev.stukalo.mealplanner.presentation.core.ui.icons.IconBack
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.component.BarcodeScannerView
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.component.CameraPermissionGate
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.component.ScannerOverlay
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.ViewIntent
import dev.stukalo.mealplanner.presentation.feature.barcodescanner.screen.contract.ViewState
import org.jetbrains.compose.resources.stringResource

/**
 * The content of the barcode scanner screen.
 *
 * @param state The current view state.
 * @param snackbarHostState The snackbar host state.
 * @param onIntent The callback for view intents.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BarcodeScannerContent(
    state: ViewState,
    snackbarHostState: SnackbarHostState,
    onIntent: (ViewIntent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera View (Full Screen)
        CameraPermissionGate {
            BarcodeScannerView(
                modifier = Modifier.fillMaxSize(),
                onBarcodeScanned = {
                    onIntent(ViewIntent.OnBarcodeScanned(it))
                }
            )
        }

        // Dark Overlay with Scanning Window
        ScannerOverlay(modifier = Modifier.fillMaxSize())

        // Header (Overlapping)
        CommonHeader(
            title = stringResource(Res.string.barcode_scanner_title),
            leftIcon = IconBack,
            leftIconTint = Theme.color.state.fixedLight,
            titleColor = Theme.color.state.fixedLight,
            onLeftIconClick = { onIntent(ViewIntent.OnBackClick) }
        )

        // Manual Entry Trigger Button
        PrimaryButton(
            text = stringResource(Res.string.barcode_scanner_manual_entry),
            onClick = { onIntent(ViewIntent.OnManualEntryClick) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = Theme.spacing.space16)
                .padding(horizontal = Theme.spacing.space16)
                .fillMaxWidth()
        )

        // Snackbar for Scanning (Top Center)
        AppSnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter)
        )

        // Manual Entry Bottom Sheet
        if (state.isManualEntryVisible) {
            ModalBottomSheet(
                onDismissRequest = { onIntent(ViewIntent.OnDismissManualEntry) },
                sheetState = sheetState,
                containerColor = Theme.color.background.secondary,
                contentColor = Theme.color.text.primary
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Theme.spacing.space16)
                ) {
                    Text(
                        text = stringResource(Res.string.barcode_scanner_enter_barcode),
                        style = Theme.typography.bold16,
                        color = Theme.color.text.primary
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.space16))

                    RoundedPlaceholderTextField(
                        value = state.barcode,
                        onValueChange = { onIntent(ViewIntent.OnBarcodeChange(it)) },
                        placeholder = stringResource(Res.string.barcode_scanner_enter_barcode),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        error = state.error
                    )

                    Spacer(modifier = Modifier.height(Theme.spacing.space24))

                    PrimaryButton(
                        text = stringResource(Res.string.common_search),
                        onClick = { onIntent(ViewIntent.OnScanClick) },
                        modifier = Modifier.fillMaxWidth(),
                        isLoading = state.isLoading
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BarcodeScannerContentPreview() {
    Theme {
        Surface(color = Theme.color.background.primary) {
            BarcodeScannerContent(
                state = ViewState(),
                snackbarHostState = SnackbarHostState(),
                onIntent = {}
            )
        }
    }
}
