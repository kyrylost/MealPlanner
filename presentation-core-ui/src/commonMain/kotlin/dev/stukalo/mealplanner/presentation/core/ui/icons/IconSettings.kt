package dev.stukalo.mealplanner.presentation.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconSettings: ImageVector
    get() {
        if (_IconSettings != null) {
            return _IconSettings!!
        }
        _IconSettings = ImageVector.Builder(
            name = "IconSettings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f
            ) {
                moveTo(12f, 15.5f)
                arcTo(3.5f, 3.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, 12f, 8.5f)
                arcTo(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 15.5f)
                close()
            }
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f
            ) {
                moveTo(19.4f, 15f)
                arcTo(1.65f, 1.65f, 0f, isMoreThanHalf = false, isPositiveArc = false, 19.1f, 18f)
                lineTo(19.9f, 18.8f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19.9f, 21.6f)
                lineTo(19.6f, 21.9f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 16.8f, 21.9f)
                lineTo(16f, 21.1f)
                arcTo(1.65f, 1.65f, 0f, isMoreThanHalf = false, isPositiveArc = false, 13f, 21.4f)
                verticalLineTo(22f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11f, 24f)
                horizontalLineTo(10f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8f, 22f)
                verticalLineTo(21.4f)
                arcTo(1.65f, 1.65f, 0f, isMoreThanHalf = false, isPositiveArc = false, 5f, 21.1f)
                lineTo(4.2f, 21.9f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.4f, 21.9f)
                lineTo(1.1f, 21.6f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.1f, 18.8f)
                lineTo(1.9f, 18f)
                arcTo(1.65f, 1.65f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.6f, 15f)
                horizontalLineTo(1f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, 13f)
                verticalLineTo(12f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 10f)
                horizontalLineTo(1.6f)
                arcTo(1.65f, 1.65f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.9f, 7f)
                lineTo(1.1f, 6.2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.1f, 3.4f)
                lineTo(1.4f, 3.1f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.2f, 3.1f)
                lineTo(5f, 3.9f)
                arcTo(1.65f, 1.65f, 0f, isMoreThanHalf = false, isPositiveArc = false, 8f, 3.6f)
                verticalLineTo(3f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 10f, 1f)
                horizontalLineTo(11f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 13f, 3f)
                verticalLineTo(3.6f)
                arcTo(1.65f, 1.65f, 0f, isMoreThanHalf = false, isPositiveArc = false, 16f, 3.9f)
                lineTo(16.8f, 3.1f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19.6f, 3.1f)
                lineTo(19.9f, 3.4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19.9f, 6.2f)
                lineTo(19.1f, 7f)
                arcTo(1.65f, 1.65f, 0f, isMoreThanHalf = false, isPositiveArc = false, 19.4f, 10f)
                horizontalLineTo(20f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 22f, 12f)
                verticalLineTo(13f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 20f, 15f)
                horizontalLineTo(19.4f)
                close()
            }
        }.build()

        return _IconSettings!!
    }

@Suppress("ObjectPropertyName")
private var _IconSettings: ImageVector? = null
