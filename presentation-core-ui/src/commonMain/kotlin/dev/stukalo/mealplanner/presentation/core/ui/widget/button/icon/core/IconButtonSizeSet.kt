package dev.stukalo.mealplanner.presentation.core.ui.widget.button.icon.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.devlight.skeleton.presentation.core.ui.component.widget.button.core.IconButtonSize
import dev.stukalo.mealplanner.presentation.core.ui.widget.button.core.IconButtonSizeSet

class IconButtonSizeSet : IconButtonSizeSet {

    override fun small(): IconButtonSize =
        object : IconButtonSize {
            override val iconSize
                @Composable
                get() = 16.dp
            override val borderSize
                @Composable
                get() = 0.dp
            override val contentPadding
                @Composable
                get() =
                    PaddingValues(
                        all = 8.dp,
                    )
            override val minHeight
                @Composable
                get() = 32.dp
            override val loadingSize
                @Composable
                get() = 16.dp
        }

    override fun default(): IconButtonSize =
        object : IconButtonSize {
            override val iconSize
                @Composable
                get() = 24.dp
            override val borderSize
                @Composable
                get() = 0.dp
            override val contentPadding
                @Composable
                get() =
                    PaddingValues(
                        all = 12.dp,
                    )
            override val minHeight
                @Composable
                get() = 40.dp
            override val loadingSize
                @Composable
                get() = 24.dp
        }
}
