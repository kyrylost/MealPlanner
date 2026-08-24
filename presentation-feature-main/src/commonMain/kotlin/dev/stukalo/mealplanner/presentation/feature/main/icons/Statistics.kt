package dev.stukalo.mealplanner.presentation.feature.main.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val NavigationBarIcons.Statistics: ImageVector
    get() {
        if (_Statistics != null) {
            return _Statistics!!
        }
        _Statistics =
            ImageVector
                .Builder(
                    name = "Statistics",
                    defaultWidth = 32.dp,
                    defaultHeight = 32.dp,
                    viewportWidth = 36f,
                    viewportHeight = 36f
                ).apply {
                    path(
                        stroke = SolidColor(Color.Black),
                        strokeLineWidth = 2.25f
                    ) {
                        moveTo(9f, 3f)
                        lineTo(27f, 3f)
                        arcTo(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 33f, 9f)
                        lineTo(33f, 27f)
                        arcTo(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 27f, 33f)
                        lineTo(9f, 33f)
                        arcTo(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, 27f)
                        lineTo(3f, 9f)
                        arcTo(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 9f, 3f)
                        close()
                    }
                    path(
                        stroke = SolidColor(Color.Black),
                        strokeLineWidth = 2.25f,
                        strokeLineCap = StrokeCap.Round
                    ) {
                        moveTo(8f, 23f)
                        lineTo(14.489f, 16.078f)
                        curveTo(14.973f, 15.562f, 15.826f, 15.698f, 16.125f, 16.339f)
                        lineTo(18.396f, 21.206f)
                        curveTo(18.69f, 21.835f, 19.519f, 21.981f, 20.01f, 21.49f)
                        lineTo(28f, 13.5f)
                    }
                }.build()

        return _Statistics!!
    }

@Suppress("ObjectPropertyName")
private var _Statistics: ImageVector? = null
