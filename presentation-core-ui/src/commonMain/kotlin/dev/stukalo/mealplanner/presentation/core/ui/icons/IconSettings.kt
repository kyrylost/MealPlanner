package dev.stukalo.mealplanner.presentation.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconSettings: ImageVector
    get() {
        if (_IconSettings != null) {
            return _IconSettings!!
        }
        _IconSettings =
            ImageVector
                .Builder(
                    name = "IconSettings",
                    defaultWidth = 32.dp,
                    defaultHeight = 32.dp,
                    viewportWidth = 32f,
                    viewportHeight = 32f
                ).apply {
                    path(
                        stroke = SolidColor(Color.Black),
                        strokeLineWidth = 2f,
                        strokeLineCap = StrokeCap.Round,
                        strokeLineJoin = StrokeJoin.Round
                    ) {
                        moveTo(16f, 12f)
                        arcTo(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, 16f, 20f)
                        arcTo(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = true, 16f, 12f)
                    }
                    path(
                        stroke = SolidColor(Color.Black),
                        strokeLineWidth = 2f,
                        strokeLineCap = StrokeCap.Round,
                        strokeLineJoin = StrokeJoin.Round
                    ) {
                        moveTo(27.758f, 10.366f)
                        lineToRelative(-1.0f, -1.732f)
                        curveToRelative(-0.552f, -0.957f, -1.775f, -1.284f, -2.732f, -0.732f)
                        lineToRelative(-0.526f, 0.304f)
                        curveToRelative(-2.0f, 1.154f, -4.5f, -0.289f, -4.5f, -2.598f)
                        verticalLineTo(5.0f)
                        curveToRelative(0.0f, -1.105f, -0.895f, -2.0f, -2.0f, -2.0f)
                        horizontalLineToRelative(-2.0f)
                        curveToRelative(-1.105f, 0.0f, -2.0f, 0.895f, -2.0f, 2.0f)
                        verticalLineToRelative(0.608f)
                        curveToRelative(0.0f, 2.309f, -2.5f, 3.753f, -4.5f, 2.598f)
                        lineTo(7.974f, 7.902f)
                        curveTo(7.017f, 7.35f, 5.794f, 7.677f, 5.242f, 8.634f)
                        lineToRelative(-1.0f, 1.732f)
                        curveToRelative(-0.552f, 0.957f, -0.225f, 2.18f, 0.732f, 2.732f)
                        lineTo(5.5f, 13.402f)
                        curveToRelative(2.0f, 1.155f, 2.0f, 4.041f, 0.0f, 5.196f)
                        lineToRelative(-0.526f, 0.304f)
                        curveToRelative(-0.957f, 0.552f, -1.284f, 1.775f, -0.732f, 2.732f)
                        lineToRelative(1.0f, 1.732f)
                        curveToRelative(0.552f, 0.957f, 1.775f, 1.284f, 2.732f, 0.732f)
                        lineTo(8.5f, 23.794f)
                        curveToRelative(2.0f, -1.155f, 4.5f, 0.289f, 4.5f, 2.598f)
                        verticalLineTo(27.0f)
                        curveToRelative(0.0f, 1.105f, 0.895f, 2.0f, 2.0f, 2.0f)
                        horizontalLineToRelative(2.0f)
                        curveToRelative(1.105f, 0.0f, 2.0f, -0.895f, 2.0f, -2.0f)
                        verticalLineToRelative(-0.608f)
                        curveToRelative(0.0f, -2.309f, 2.5f, -3.753f, 4.5f, -2.598f)
                        lineToRelative(0.526f, 0.304f)
                        curveToRelative(0.957f, 0.552f, 2.18f, 0.225f, 2.732f, -0.732f)
                        lineToRelative(1.0f, -1.732f)
                        curveToRelative(0.552f, -0.957f, 0.225f, -2.18f, -0.732f, -2.732f)
                        lineTo(26.5f, 18.598f)
                        curveToRelative(-2.0f, -1.155f, -2.0f, -4.041f, 0.0f, -5.196f)
                        lineToRelative(0.526f, -0.304f)
                        curveTo(27.983f, 12.546f, 28.311f, 11.323f, 27.758f, 10.366f)
                        close()
                    }
                }.build()

        return _IconSettings!!
    }

@Suppress("ObjectPropertyName")
private var _IconSettings: ImageVector? = null
