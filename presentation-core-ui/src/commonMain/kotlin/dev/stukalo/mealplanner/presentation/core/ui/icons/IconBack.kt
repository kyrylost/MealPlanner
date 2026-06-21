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
        _IconBack = ImageVector.Builder(
            name = "IconBack",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f
            ) {
                moveTo(19f, 12f)
                horizontalLineTo(5f)
                moveTo(5f, 12f)
                lineTo(12f, 19f)
                moveTo(5f, 12f)
                lineTo(12f, 5f)
            }
        }.build()

        return _IconBack!!
    }

@Suppress("ObjectPropertyName")
private var _IconBack: ImageVector? = null
