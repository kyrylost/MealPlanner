package dev.stukalo.mealplanner.presentation.feature.main.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val NavigationBarIcons.IconSettings: ImageVector
    get() {
        if (_IconSettings != null) {
            return _IconSettings!!
        }
        _IconSettings = ImageVector.Builder(
            name = "IconSettings",
            defaultWidth = 48.dp,
            defaultHeight = 48.dp,
            viewportWidth = 48f,
            viewportHeight = 48f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(39.23f, 26f)
                arcToRelative(16.52f, 16.52f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.14f, -2f)
                arcToRelative(16.52f, 16.52f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.14f, -2f)
                lineToRelative(4.33f, -3.39f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.25f, -1.31f)
                lineToRelative(-4.1f, -7.11f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.25f, -0.44f)
                lineToRelative(-5.11f, 2.06f)
                arcToRelative(15.68f, 15.68f, 0f, isMoreThanHalf = false, isPositiveArc = false, -3.46f, -2f)
                lineToRelative(-0.77f, -5.43f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1f, -0.86f)
                horizontalLineTo(19.9f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1f, 0.86f)
                lineToRelative(-0.77f, 5.43f)
                arcToRelative(15.36f, 15.36f, 0f, isMoreThanHalf = false, isPositiveArc = false, -3.46f, 2f)
                lineTo(9.54f, 9.75f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.25f, 0.44f)
                lineTo(4.19f, 17.3f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.25f, 1.31f)
                lineTo(8.76f, 22f)
                arcToRelative(16.66f, 16.66f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.14f, 2f)
                arcToRelative(16.52f, 16.52f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.14f, 2f)
                lineTo(4.44f, 29.39f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, 1.31f)
                lineToRelative(4.1f, 7.11f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.25f, 0.44f)
                lineToRelative(5.11f, -2.06f)
                arcToRelative(15.68f, 15.68f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.46f, 2f)
                lineToRelative(0.77f, 5.43f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1f, 0.86f)
                horizontalLineToRelative(8.2f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1f, -0.86f)
                lineToRelative(0.77f, -5.43f)
                arcToRelative(15.36f, 15.36f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.46f, -2f)
                lineToRelative(5.11f, 2.06f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.25f, -0.44f)
                lineToRelative(4.1f, -7.11f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, -1.31f)
                close()
                moveTo(24f, 31.18f)
                arcTo(7.18f, 7.18f, 0f, isMoreThanHalf = true, isPositiveArc = true, 31.17f, 24f)
                arcTo(7.17f, 7.17f, 0f, isMoreThanHalf = false, isPositiveArc = true, 24f, 31.18f)
                close()
            }
        }.build()

        return _IconSettings!!
    }

@Suppress("ObjectPropertyName")
private var _IconSettings: ImageVector? = null
