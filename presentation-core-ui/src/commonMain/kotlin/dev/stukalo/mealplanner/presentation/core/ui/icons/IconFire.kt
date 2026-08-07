package dev.stukalo.mealplanner.presentation.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconFire: ImageVector
    get() {
        if (_IconFire != null) {
            return _IconFire!!
        }
        _IconFire = ImageVector.Builder(
            name = "IconFire",
            defaultWidth = 800.dp,
            defaultHeight = 800.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(11.176f, 11.53f)
                curveTo(11.722f, 10.763f, 11.661f, 9.955f, 11.282f, 8.502f)
                curveTo(10.533f, 5.629f, 10.831f, 4.054f, 13.415f, 2.189f)
                lineTo(14.676f, 1.279f)
                lineTo(14.981f, 2.804f)
                curveTo(15.305f, 4.424f, 15.837f, 5.427f, 17.204f, 7.355f)
                curveTo(17.258f, 7.431f, 17.258f, 7.431f, 17.312f, 7.508f)
                curveTo(19.281f, 10.285f, 20f, 11.96f, 20f, 15f)
                curveTo(20f, 18.688f, 16.271f, 22f, 12f, 22f)
                curveTo(7.728f, 22f, 4f, 18.689f, 4f, 15f)
                curveTo(4f, 14.931f, 4f, 14.933f, 3.988f, 14.628f)
                curveTo(3.898f, 12.272f, 4.334f, 10.427f, 6.097f, 8.436f)
                curveTo(6.47f, 8.015f, 6.893f, 7.611f, 7.37f, 7.224f)
                lineTo(8.422f, 6.369f)
                lineTo(8.928f, 7.627f)
                curveTo(9.302f, 8.555f, 9.74f, 9.286f, 10.235f, 9.822f)
                curveTo(10.654f, 10.275f, 10.965f, 10.846f, 11.176f, 11.53f)
                close()
                moveTo(7.594f, 9.762f)
                curveTo(6.237f, 11.295f, 5.914f, 12.661f, 5.987f, 14.552f)
                curveTo(6f, 14.904f, 6f, 14.892f, 6f, 15f)
                curveTo(6f, 17.528f, 8.784f, 20f, 12f, 20f)
                curveTo(15.216f, 20f, 18f, 17.527f, 18f, 15f)
                curveTo(18f, 12.458f, 17.432f, 11.135f, 15.681f, 8.665f)
                curveTo(15.626f, 8.588f, 15.626f, 8.588f, 15.572f, 8.511f)
                curveTo(14.509f, 7.011f, 13.875f, 5.968f, 13.455f, 4.801f)
                curveTo(12.77f, 5.621f, 12.811f, 6.437f, 13.218f, 7.998f)
                curveTo(13.967f, 10.871f, 13.669f, 12.446f, 11.085f, 14.311f)
                lineTo(9.612f, 15.374f)
                lineTo(9.502f, 13.561f)
                curveTo(9.431f, 12.402f, 9.169f, 11.616f, 8.765f, 11.178f)
                curveTo(8.367f, 10.747f, 8.006f, 10.241f, 7.684f, 9.663f)
                curveTo(7.653f, 9.696f, 7.624f, 9.729f, 7.594f, 9.762f)
                close()
            }
        }.build()

        return _IconFire!!
    }

@Suppress("ObjectPropertyName")
private var _IconFire: ImageVector? = null
