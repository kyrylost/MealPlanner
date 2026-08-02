package dev.stukalo.mealplanner.presentation.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconBack: ImageVector
    get() {
        if (_IconBack != null) {
            return _IconBack!!
        }
        _IconBack =
            ImageVector
                .Builder(
                    name = "IconBack",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                ).apply {
                    path(fill = SolidColor(Color.Black)) {
                        moveTo(19f, 11f)
                        horizontalLineTo(7.83f)
                        lineToRelative(4.88f, -4.88f)
                        curveToRelative(0.39f, -0.39f, 0.39f, -1.03f, 0f, -1.42f)
                        arcToRelative(
                            0.996f,
                            0.996f,
                            0f,
                            isMoreThanHalf = false,
                            isPositiveArc = false,
                            -1.41f,
                            0f
                        )
                        lineToRelative(-6.59f, 6.59f)
                        arcToRelative(
                            0.996f,
                            0.996f,
                            0f,
                            isMoreThanHalf = false,
                            isPositiveArc = false,
                            0f,
                            1.41f
                        )
                        lineToRelative(6.59f, 6.59f)
                        arcToRelative(
                            0.996f,
                            0.996f,
                            0f,
                            isMoreThanHalf = true,
                            isPositiveArc = false,
                            1.41f,
                            -1.41f
                        )
                        lineTo(7.83f, 13f)
                        horizontalLineTo(19f)
                        curveToRelative(0.55f, 0f, 1f, -0.45f, 1f, -1f)
                        reflectiveCurveToRelative(-0.45f, -1f, -1f, -1f)
                    }
                }.build()

        return _IconBack!!
    }

@Suppress("ObjectPropertyName")
private var _IconBack: ImageVector? = null
