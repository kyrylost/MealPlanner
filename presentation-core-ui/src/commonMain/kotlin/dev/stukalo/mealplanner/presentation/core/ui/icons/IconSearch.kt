package dev.stukalo.mealplanner.presentation.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconSearch: ImageVector
    get() {
        if (_IconSearch != null) {
            return _IconSearch!!
        }
        _IconSearch = ImageVector.Builder(
            name = "ic_search",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 1024f,
            viewportHeight = 1024f
        ).apply {
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(398.9f, 735.5f)
                curveToRelative(-185.4f, 0f, -336.3f, -150.9f, -336.3f, -336.3f)
                curveTo(62.7f, 213.9f, 213.5f, 63f, 398.9f, 63f)
                reflectiveCurveToRelative(336.2f, 150.9f, 336.2f, 336.2f)
                curveToRelative(0.1f, 185.4f, -150.8f, 336.3f, -336.2f, 336.3f)
                moveToRelative(0f, -597.8f)
                curveToRelative(-144.2f, 0f, -261.5f, 117.3f, -261.5f, 261.5f)
                reflectiveCurveToRelative(117.4f, 261.5f, 261.5f, 261.5f)
                curveToRelative(144.2f, 0f, 261.5f, -117.3f, 261.5f, -261.5f)
                curveToRelative(0.1f, -144.2f, -117.3f, -261.5f, -261.5f, -261.5f)
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(865.9f, 959.6f)
                curveToRelative(-23.9f, 0f, -47.8f, -9.1f, -66f, -27.3f)
                lineTo(540.3f, 672.6f)
                curveToRelative(-14.6f, -14.6f, -14.6f, -38.3f, 0f, -52.8f)
                curveToRelative(14.6f, -14.6f, 38.2f, -14.6f, 52.8f, 0f)
                lineToRelative(259.6f, 259.7f)
                curveToRelative(7.4f, 7.4f, 19f, 7.4f, 26.4f, 0f)
                curveToRelative(3.7f, -3.7f, 5.5f, -8.1f, 5.5f, -13.2f)
                reflectiveCurveToRelative(-1.9f, -9.5f, -5.5f, -13.2f)
                lineTo(717.9f, 691.8f)
                curveToRelative(-14.6f, -14.6f, -14.6f, -38.2f, 0f, -52.8f)
                curveToRelative(14.6f, -14.6f, 38.2f, -14.6f, 52.8f, 0f)
                lineToRelative(161.2f, 161.2f)
                curveToRelative(17.7f, 17.7f, 27.4f, 41.1f, 27.4f, 66f)
                reflectiveCurveToRelative(-9.7f, 48.3f, -27.4f, 66f)
                curveToRelative(-18.2f, 18.2f, -42.1f, 27.4f, -66f, 27.4f)
                moveTo(249.5f, 436.6f)
                curveToRelative(-20.6f, 0f, -37.3f, -16.7f, -37.3f, -37.4f)
                curveToRelative(0f, -103f, 83.8f, -186.8f, 186.8f, -186.8f)
                curveToRelative(20.6f, 0f, 37.3f, 16.7f, 37.3f, 37.4f)
                curveToRelative(0f, 20.6f, -16.7f, 37.3f, -37.3f, 37.3f)
                curveToRelative(-61.8f, 0f, -112.1f, 50.3f, -112.1f, 112.1f)
                curveToRelative(0f, 20.6f, -16.8f, 37.4f, -37.4f, 37.4f)
            }
        }.build()

        return _IconSearch!!
    }

@Suppress("ObjectPropertyName")
private var _IconSearch: ImageVector? = null
