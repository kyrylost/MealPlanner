package dev.stukalo.mealplanner.presentation.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconFilter: ImageVector
    get() {
        if (_IconFilter != null) {
            return _IconFilter!!
        }
        _IconFilter = ImageVector.Builder(
            name = "ic_filter",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(10f, 18f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(-4f)
                verticalLineToRelative(2f)
                close()
                moveTo(3f, 6f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(18f)
                lineTo(21f, 6f)
                lineTo(3f, 6f)
                close()
                moveTo(6f, 13f)
                horizontalLineToRelative(12f)
                verticalLineToRelative(-2f)
                lineTo(6f, 11f)
                verticalLineToRelative(2f)
                close()
            }
        }.build()
        return _IconFilter!!
    }

private var _IconFilter: ImageVector? = null
