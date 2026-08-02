package dev.stukalo.mealplanner.presentation.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconClock: ImageVector
    get() {
        if (_IconClock != null) {
            return _IconClock!!
        }
        _IconClock =
            ImageVector
                .Builder(
                    name = "IconClock",
                    defaultWidth = 800.dp,
                    defaultHeight = 800.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                ).apply {
                    path(
                        stroke = SolidColor(Color.Black),
                        strokeLineWidth = 1.5f,
                        strokeLineCap = StrokeCap.Round,
                        strokeLineJoin = StrokeJoin.Round
                    ) {
                        moveTo(22f, 12f)
                        curveTo(22f, 17.52f, 17.52f, 22f, 12f, 22f)
                        curveTo(6.48f, 22f, 2f, 17.52f, 2f, 12f)
                        curveTo(2f, 6.48f, 6.48f, 2f, 12f, 2f)
                        curveTo(17.52f, 2f, 22f, 6.48f, 22f, 12f)
                        close()
                    }
                    path(
                        stroke = SolidColor(Color.Black),
                        strokeLineWidth = 1.5f,
                        strokeLineCap = StrokeCap.Round,
                        strokeLineJoin = StrokeJoin.Round
                    ) {
                        moveTo(15.71f, 15.18f)
                        lineTo(12.61f, 13.33f)
                        curveTo(12.07f, 13.01f, 11.63f, 12.24f, 11.63f, 11.61f)
                        verticalLineTo(7.51f)
                    }
                }.build()

        return _IconClock!!
    }

@Suppress("ObjectPropertyName")
private var _IconClock: ImageVector? = null
