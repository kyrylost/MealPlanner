package dev.stukalo.mealplanner.presentation.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IconEdit: ImageVector
    get() {
        if (_IconEdit != null) {
            return _IconEdit!!
        }
        _IconEdit =
            ImageVector
                .Builder(
                    name = "IconEdit",
                    defaultWidth = 800.dp,
                    defaultHeight = 800.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                ).apply {
                    path(
                        fill = SolidColor(Color.Black),
                        pathFillType = PathFillType.EvenOdd
                    ) {
                        moveTo(15.909f, 3.874f)
                        curveTo(16.468f, 3.314f, 17.227f, 3f, 18.018f, 3f)
                        curveTo(18.409f, 3f, 18.797f, 3.077f, 19.159f, 3.227f)
                        curveTo(19.521f, 3.377f, 19.85f, 3.597f, 20.126f, 3.874f)
                        curveTo(20.403f, 4.15f, 20.623f, 4.479f, 20.773f, 4.841f)
                        curveTo(20.923f, 5.203f, 21f, 5.591f, 21f, 5.982f)
                        curveTo(21f, 6.374f, 20.923f, 6.762f, 20.773f, 7.124f)
                        curveTo(20.623f, 7.486f, 20.403f, 7.814f, 20.126f, 8.091f)
                        lineTo(19.023f, 9.195f)
                        curveTo(18.633f, 9.585f, 17.999f, 9.585f, 17.609f, 9.195f)
                        lineTo(14.805f, 6.391f)
                        curveTo(14.415f, 6.001f, 14.415f, 5.367f, 14.805f, 4.977f)
                        lineTo(15.909f, 3.874f)
                        close()
                        moveTo(13.391f, 7.805f)
                        curveTo(13.001f, 7.415f, 12.367f, 7.415f, 11.977f, 7.805f)
                        lineTo(5.011f, 14.771f)
                        curveTo(4.37f, 15.412f, 3.915f, 16.215f, 3.696f, 17.094f)
                        lineTo(3.03f, 19.757f)
                        curveTo(2.945f, 20.098f, 3.045f, 20.459f, 3.293f, 20.707f)
                        curveTo(3.541f, 20.955f, 3.902f, 21.055f, 4.243f, 20.97f)
                        lineTo(6.906f, 20.304f)
                        curveTo(7.785f, 20.085f, 8.588f, 19.63f, 9.229f, 18.989f)
                        lineTo(16.195f, 12.023f)
                        curveTo(16.585f, 11.633f, 16.585f, 10.999f, 16.195f, 10.609f)
                        lineTo(13.391f, 7.805f)
                        close()
                    }
                    path(
                        fill = SolidColor(Color.Black),
                        pathFillType = PathFillType.EvenOdd
                    ) {
                        moveTo(12f, 20f)
                        curveTo(12f, 19.448f, 12.448f, 19f, 13f, 19f)
                        lineTo(20f, 19f)
                        curveTo(20.552f, 19f, 21f, 19.448f, 21f, 20f)
                        curveTo(21f, 20.552f, 20.552f, 21f, 20f, 21f)
                        lineTo(13f, 21f)
                        curveTo(12.448f, 21f, 12f, 20.552f, 12f, 20f)
                        close()
                    }
                }.build()

        return _IconEdit!!
    }

@Suppress("ObjectPropertyName")
private var _IconEdit: ImageVector? = null
