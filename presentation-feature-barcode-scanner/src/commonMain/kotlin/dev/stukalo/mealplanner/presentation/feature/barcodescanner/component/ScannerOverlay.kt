package dev.stukalo.mealplanner.presentation.feature.barcodescanner.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stukalo.mealplanner.presentation.core.styling.Theme

/**
 * A visual overlay for the barcode scanner that dims the background
 * and leaves a clear "window" in the center for scanning.
 *
 * @param modifier The modifier to be applied to the overlay.
 */
@Composable
fun ScannerOverlay(modifier: Modifier = Modifier) {
    val strokeColor = Theme.color.brand.primary
    val overlayColor = Theme.color.state.fixedDark.copy(alpha = OVERLAY_ALPHA)
    val cornerRadius = Theme.radius.radius16
    val strokeWidth = STROKE_WIDTH.dp

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Define the scanning window size
        val windowWidth = width * WINDOW_WIDTH_FRACTION
        val windowHeight = windowWidth * WINDOW_ASPECT_RATIO

        val left = (width - windowWidth) / 2
        val top = (height - windowHeight) / 2

        val windowRect = Rect(left, top, left + windowWidth, top + windowHeight)
        val windowPath = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = windowRect,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            )
        }

        // Draw the dimmed overlay everywhere EXCEPT the window
        clipPath(windowPath, clipOp = ClipOp.Difference) {
            drawRect(color = overlayColor)
        }

        // Draw the border around the window
        drawPath(
            path = windowPath,
            color = strokeColor,
            style = Stroke(width = strokeWidth.toPx())
        )
    }
}

/**
 * The alpha transparency for the dimmed area of the overlay.
 */
internal const val OVERLAY_ALPHA = 0.5f

/**
 * The fraction of the screen width that the scanning window occupies.
 */
internal const val WINDOW_WIDTH_FRACTION = 0.7f

/**
 * The aspect ratio (height/width) of the scanning window.
 */
internal const val WINDOW_ASPECT_RATIO = 0.6f

/**
 * The width of the border stroke around the scanning window.
 */
internal const val STROKE_WIDTH = 2f

@Preview
@Composable
private fun ScannerOverlayPreview() {
    Theme {
        ScannerOverlay(modifier = Modifier.fillMaxSize())
    }
}
